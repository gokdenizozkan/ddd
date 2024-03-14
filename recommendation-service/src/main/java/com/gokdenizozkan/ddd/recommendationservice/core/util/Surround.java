package com.gokdenizozkan.ddd.recommendationservice.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;

import java.io.IOException;

@Slf4j
public class Surround {
    public static QueryResponse withTryCatch(SolrClient client, String collectionName, SolrQuery query) {
        try {
            return client.query(collectionName, query);
        } catch (SolrServerException | IOException e) {
            log.error("Error while querying solr with collectionName {}, query {}, client {}, error: {}", collectionName, query, client, e.getMessage());
            e.printStackTrace();
        }
        return new QueryResponse();
    }

    public static UpdateResponse withTryCatch(SolrClient client, String collectionName, UpdateRequest request) {
        try {
            return request.process(client, collectionName);
        } catch (SolrServerException | IOException e) {
            log.error("Error while updating solr with collectionName {}, request {}, client {}, error: {}", collectionName, request, client, e.getMessage());
            e.printStackTrace();
        }
        return new UpdateResponse();
    }
}
