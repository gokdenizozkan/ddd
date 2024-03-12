package com.gokdenizozkan.ddd.recommendationservice.feature.radar;

import com.gokdenizozkan.ddd.recommendationservice.config.SolrClientProvider;
import com.gokdenizozkan.ddd.recommendationservice.feature.radar.RadarEngine;
import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("FoodStoreRadarEngine")
@Getter
@Setter
public class FoodStoreRadarEngine implements RadarEngine {
    private final SolrClient client;

    public FoodStoreRadarEngine(SolrClientProvider provider, @Value("${engine.recommendation-engine.base-solr-url}") String baseSolrUrl) {
        this.client = provider.http2(baseSolrUrl);
    }

}
