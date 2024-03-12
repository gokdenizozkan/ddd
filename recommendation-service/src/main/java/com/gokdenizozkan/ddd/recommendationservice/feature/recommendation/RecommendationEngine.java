package com.gokdenizozkan.ddd.recommendationservice.feature.recommendation;

import com.gokdenizozkan.ddd.recommendationservice.config.SolrClientProvider;
import com.gokdenizozkan.ddd.recommendationservice.core.SolrSpatialQuery;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RecommendationEngine {
    private final SolrClient client;

    public RecommendationEngine(SolrClientProvider provider, @Value("${engine.recommendation-engine.base-solr-url}") String baseSolrUrl) {
        this.client = provider.http2(baseSolrUrl);
    }

    public QueryResponse findWithinRadius(String collectionName, String query, // required
                                          String point, String latlonFieldName, Integer km, // required
                                          SolrSpatialQuery.SortOrder sortOrder,
                                          String fieldList,
                                          Integer rows) {
        return SolrSpatialQuery.of(collectionName)
                .q(query)
                .point(point)
                .distance(km)
                .latlonFieldName(latlonFieldName)
                .filter(SolrSpatialQuery.FilterType.GEOFILT)
                .sort(SolrSpatialQuery.SortType.GEODIST, sortOrder)
                .fieldList(fieldList)
                .rows(rows)
                .execute(client);
    }

}
