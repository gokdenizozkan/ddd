package com.gokdenizozkan.ddd.recommendationservice.core.solrquery;

import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

@Slf4j
public class SolrFetchQuery {
    private SolrQuery query;
    private String collectionName;

    private SolrFetchQuery(String collectionName) {
        this.query = new SolrQuery();
        this.collectionName = collectionName;
    }

    public static SolrFetchQuery of(String collectionName) {
        log.info("Building SolrFetchQuery for collection: {}", collectionName);
        return new SolrFetchQuery(collectionName);
    }

    public SolrFetchQuery findAll() {
        log.info("Setting query to *:* for SolrFetchQuery");
        query.setQuery("*:*");
        return this;
    }

    public SolrFetchQuery findById(String id) {
        log.info("Setting query to id:{} for SolrFetchQuery", id);
        query.setQuery(new StringBuilder("id:").append(id).toString());
        return this;
    }

    public SolrFetchQuery findBy(String query) {
        log.info("Setting query to {} for SolrFetchQuery", query);
        this.query.setQuery(query);
        return this;
    }

    public SolrQuery build() {
        log.info("build called, returning SolrQuery");
        return query;
    }

    public QueryResponse _process(SolrClient client) {
        log.info("Processing SolrQuery with client: {}", client);
        try {
            return client.query(collectionName, query);
        } catch (Exception e) {
            log.error("Error while processing SolrQuery with client: {}", e.getMessage());
            return new QueryResponse();
        }
    }

    public SolrDocument _processAndGetFirstResult(SolrClient client) {
        log.info("Processing SolrQuery ({}) with client: {}", query.toQueryString(), client);
        try {
            SolrDocument found = client.query(collectionName, query).getResults().getFirst();
            log.info("Found document: {}", found);
            return found;
        } catch (Exception e) {
            log.error("Error while processing SolrQuery with client: {}", e.getMessage());
            return new SolrDocument();
        }
    }
}
