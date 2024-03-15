package com.gokdenizozkan.ddd.recommendationservice.feature.indexing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.gokdenizozkan.ddd.recommendationservice.config.SolrClientProvider;
import com.gokdenizozkan.ddd.recommendationservice.core.solrquery.SolrFetchQuery;
import com.gokdenizozkan.ddd.recommendationservice.core.solrquery.UpdateRequestQuery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
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
@Slf4j
public class IndexingEngine {
    private final SolrClient client;

    public IndexingEngine(SolrClientProvider provider, @Value("${ddd.recommendation-service.base-solr-url}") String baseSolrUrl) {
        this.client = provider.http2(baseSolrUrl);
    }

    public <T> UpdateResponse index(String collectionName, T document) {
        try {
            client.addBean(collectionName, document);
            return client.commit(collectionName);
        } catch (SolrServerException | IOException e) {
            log.error("Error while indexing document: {}", e.getMessage());
            e.printStackTrace();
        }
        return new UpdateResponse();
    }

    public <T> UpdateResponse indexCollection(@NotBlank String collectionName, @NotNull Collection<T> documents) {
        try {
            client.addBeans(collectionName, documents);
            return client.commit(collectionName);
        } catch (IOException | SolrServerException e) {
            log.error("Error while indexing collection of documents: {}", e.getMessage());
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
            log.error("Error while reading the file at {}: {}", json.toPath(), e.getMessage());
            e.printStackTrace();
        }
        return indexCollection(collectionName, documents);
    }

    public UpdateResponse updateById(@NotBlank String collectionName, @NotBlank String id, @NotBlank String field, @NotBlank String newValue) {
        log.info("Finding the existing document with id: {}", id);
        SolrDocument foundDoc = SolrFetchQuery.of(collectionName)
                .findById(id)
                ._processAndGetFirstResult(client);

        log.info("Creating a new document with the new value {} for the field {}", newValue, field);
        SolrInputDocument newDoc = new SolrInputDocument();
        newDoc.addField(field, newValue);
        for (Map.Entry<String, Object> entry : foundDoc.entrySet()) {
            if (entry.getKey().equals(field) || entry.getKey().equals("_version_") || entry.getKey().equals("name_exact")) {
                continue;
            }
            newDoc.addField(entry.getKey(), entry.getValue());
        }

        return UpdateRequestQuery.of(collectionName)
                .add(newDoc)
                .setCommitTrue()
                .setOverwriteTrue()
                .setCommitWithin(1000)
                ._process(client);
    }

    public UpdateResponse updateById(@NotBlank String collectionName, @NotBlank String id, @NotNull Map<String, Object> fields) {
        log.info("Finding the existing document with id: {}", id);
        SolrDocument foundDoc = SolrFetchQuery.of(collectionName)
                .findById(id)
                ._processAndGetFirstResult(client);

        log.info("Creating a new document with the new values: {}", fields);
        SolrInputDocument newDoc = new SolrInputDocument();
        fields.forEach(newDoc::addField);

        foundDoc.forEach((key, value) -> {
            if (fields.containsKey(key) || key.equals("_version_") || key.equals("name_exact")) {
                return;
            }
            newDoc.addField(key, value);
        });

        return UpdateRequestQuery.of(collectionName)
                .add(newDoc)
                .setCommitTrue()
                .setOverwriteTrue()
                .setCommitWithin(1000)
                ._process(client);
    }

    public UpdateResponse deleteById(@NotBlank String collectionName, @NotBlank String id) {
        try {
            return client.deleteById(collectionName, id);
        } catch (IOException | SolrServerException e) {
            log.error("Error while deleting the document with id {} found at {}", id, collectionName);
            e.printStackTrace();
        }
        return new UpdateResponse();
    }

    public UpdateResponse deleteAll(@NotBlank String collectionName) {
        try {
            return client.deleteByQuery(collectionName, "*:*");
        } catch (IOException | SolrServerException e) {
            log.error("Error while deleting all documents in the collection: {}", collectionName);
            e.printStackTrace();
        }
        return new UpdateResponse();
    }
}
