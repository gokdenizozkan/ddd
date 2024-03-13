package com.gokdenizozkan.ddd.recommendationservice.core.util;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;

import java.io.IOException;

public class Surround {
    public static QueryResponse withTryCatch(SolrClient client, String collectionName, SolrQuery query) {
        try {
            return client.query(collectionName, query);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }

    public static UpdateResponse withTryCatch(SolrClient client, String collectionName, UpdateRequest request) {
        try {
            return request.process(client, collectionName);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return new UpdateResponse();
    }
}
