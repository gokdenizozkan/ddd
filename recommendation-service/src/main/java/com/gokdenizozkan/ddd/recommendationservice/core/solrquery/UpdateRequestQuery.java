package com.gokdenizozkan.ddd.recommendationservice.core.solrquery;

import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

@Slf4j
public class UpdateRequestQuery {
    private UpdateRequest request;
    private String collectionName;

    private UpdateRequestQuery(String collectionName) {
        this.request = new UpdateRequest();
        this.request.setMethod(UpdateRequest.METHOD.POST);

        this.collectionName = collectionName;
    }

    public static UpdateRequestQuery of(String collectionName) {
        log.info("Building UpdateRequestQuery for collection: {}", collectionName);
        return new UpdateRequestQuery(collectionName);
    }

    public UpdateRequestQuery add(SolrInputDocument document) {
        log.info("Adding document with field names {} to UpdateRequestQuery", document.getFieldNames());
        request.add(document);
        return this;
    }

    public UpdateRequestQuery setCommitTrue() {
        log.info("Setting commit to true for UpdateRequestQuery");
        request.setParam("commit", "true");
        return this;
    }

    public UpdateRequestQuery setOverwriteTrue() {
        log.info("Setting overwrite to true for UpdateRequestQuery");
        request.setParam("overwrite", "true");
        return this;
    }

    public UpdateRequestQuery setCommitWithin(Integer milliseconds) {
        log.info("Setting commitWithin to {} for UpdateRequestQuery", milliseconds);
        request.setCommitWithin(milliseconds);
        return this;
    }

    public UpdateRequest build() {
        log.info("build called, returning UpdateRequest");
        return request;
    }

    public UpdateResponse _process(SolrClient client) {
        log.info("Processing UpdateRequest with client: {}", client);
        try {
            return request.process(client, collectionName);
        } catch (Exception e) {
            log.error("Error while processing UpdateRequest with client: {}", e.getMessage());
            return new UpdateResponse();
        }
    }
}
