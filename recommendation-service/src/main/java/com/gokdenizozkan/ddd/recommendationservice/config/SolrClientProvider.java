package com.gokdenizozkan.ddd.recommendationservice.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.springframework.stereotype.Component;

@Component
public class SolrClientProvider {
    private SolrClient client;

    public SolrClient http2(String baseSolrUrl) {
        if (client != null) {
            return client;
        }
        client = new Http2SolrClient.Builder(baseSolrUrl).build();
        return client;
    }

    public SolrClient http2(String baseSolrUrl, boolean override) {
        if (!override) {
            return http2(baseSolrUrl);
        }
        client = new Http2SolrClient.Builder(baseSolrUrl).build();
        return client;
    }
}
