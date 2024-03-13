package com.gokdenizozkan.ddd.recommendationservice.feature.indexing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.gokdenizozkan.ddd.recommendationservice.config.SolrClientProvider;
import com.gokdenizozkan.ddd.recommendationservice.core.util.Surround;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class IndexingEngine {
    private final SolrClient client;

    public IndexingEngine(SolrClientProvider provider, @Value("${engine.indexing-engine.base-solr-url}") String baseSolrUrl) {
        this.client = provider.http2(baseSolrUrl);

        updateById("dddfoodstores", "1", "name", "Gokdeniz's Food Store");
    }

    public <T> UpdateResponse index(String collectionName, T document) {
        try {
            client.addBean(collectionName, document);
            return client.commit(collectionName);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return new UpdateResponse();
    }

    public <T> UpdateResponse indexCollection(@NotBlank String collectionName, @NotNull Collection<T> documents) {
        try {
            client.addBeans(collectionName, documents);
            return client.commit(collectionName);
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
        return new UpdateResponse();
    }

    public <T> UpdateResponse indexCollection(@NotBlank String collectionName, @NotNull File json, @NotNull Class<T> classToMap) {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, classToMap);
        Collection<T> documents = null;
        try {
            documents = mapper.readValue(json, collectionType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexCollection(collectionName, documents);
    }

    public UpdateResponse updateById(@NotBlank String collectionName, @NotBlank String id, @NotBlank String field, @NotBlank String newValue) {
        // find the existing document
        SolrQuery getQuery = new SolrQuery();
        getQuery.setQuery("id:" + id);

        QueryResponse queryResponse = Surround.withTryCatch(client, collectionName, getQuery);
        SolrDocument foundDoc = queryResponse.getResults().get(0);

        // create a new document with the new value
        SolrInputDocument newDoc = new SolrInputDocument();
        newDoc.addField(field, newValue);
        for (Map.Entry<String, Object> entry : foundDoc.entrySet()) {
            if (entry.getKey().equals(field) || entry.getKey().equals("_version_") || entry.getKey().equals("name_exact")) {
                continue;
            }

            newDoc.addField(entry.getKey(), entry.getValue());
        }

        // prepare the update request and process it
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.add(newDoc);
        updateRequest.setCommitWithin(1000);
        updateRequest.setMethod(SolrRequest.METHOD.POST);
        updateRequest.setParam("overwrite", "true");
        updateRequest.setParam("commit", "true");

        return Surround.withTryCatch(client, collectionName, updateRequest);
    }

    public UpdateResponse updateById(@NotBlank String collectionName, @NotBlank String id, @NotNull Map<String, Object> fields) {
        // find the existing document
        SolrQuery getQuery = new SolrQuery();
        getQuery.setQuery("id:" + id);

        QueryResponse queryResponse = Surround.withTryCatch(client, collectionName, getQuery);
        SolrDocument foundDoc = queryResponse.getResults().get(0);

        // create a new document with the new value
        SolrInputDocument newDoc = new SolrInputDocument();
        fields.forEach(newDoc::addField);

        foundDoc.forEach((key, value) -> {
            if (fields.containsKey(key) || key.equals("_version_") || key.equals("name_exact")) {
                return;
            }
            newDoc.addField(key, value);
        });

        // prepare the update request and process it
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.add(newDoc);
        updateRequest.setCommitWithin(1000);
        updateRequest.setMethod(SolrRequest.METHOD.POST);
        updateRequest.setParam("overwrite", "true");
        updateRequest.setParam("commit", "true");

        return Surround.withTryCatch(client, collectionName, updateRequest);
    }

    public UpdateResponse deleteById(@NotBlank String collectionName, @NotBlank String id) {
        try {
            return client.deleteById(collectionName, id);
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
        return new UpdateResponse();
    }

    public UpdateResponse deleteAll(@NotBlank String collectionName) {
        try {
            return client.deleteByQuery(collectionName, "*:*");
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
        return new UpdateResponse();
    }
}
