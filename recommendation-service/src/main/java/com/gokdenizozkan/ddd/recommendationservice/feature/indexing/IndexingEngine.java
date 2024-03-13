package com.gokdenizozkan.ddd.recommendationservice.feature.indexing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.gokdenizozkan.ddd.recommendationservice.config.SolrClientProvider;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.params.UpdateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Service
public class IndexingEngine {
    private final SolrClient client;

    public IndexingEngine(SolrClientProvider provider, @Value("${engine.indexing-engine.base-solr-url}") String baseSolrUrl) {
        this.client = provider.http2(baseSolrUrl);
    }

    public <T> UpdateResponse index(String collection, T document) throws SolrServerException, IOException {
        UpdateResponse response = client.addBean(collection, document);
        return client.commit(collection);
    }

    public <T> UpdateResponse indexCollection(String collectionName, Collection<T> documents) throws SolrServerException, IOException {
        UpdateResponse response = client.addBeans(collectionName, documents);
        response.jsonStr();
        return client.commit(collectionName);
    }

    public <T> UpdateResponse indexCollection(String collectionName, File json, Class<T> classToMap) throws SolrServerException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, classToMap);
        Collection<T> documents = mapper.readValue(json, collectionType);
        return indexCollection(collectionName, documents);
    }
}
