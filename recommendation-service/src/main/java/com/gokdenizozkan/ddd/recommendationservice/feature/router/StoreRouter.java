package com.gokdenizozkan.ddd.recommendationservice.feature.router;

import com.gokdenizozkan.ddd.recommendationservice.core.datastructure.Tuple;
import com.gokdenizozkan.ddd.recommendationservice.core.spatial.SpatialQueryArchetype;
import com.gokdenizozkan.ddd.recommendationservice.core.util.Converter;
import com.gokdenizozkan.ddd.recommendationservice.core.util.ResponseUtil;
import com.gokdenizozkan.ddd.recommendationservice.entity.Rating;
import com.gokdenizozkan.ddd.recommendationservice.entity.foodstore.dto.response.FoodStoreResponse;
import com.gokdenizozkan.ddd.recommendationservice.feature.radar.RadarEngine;
import com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.RecommendationEngine;
import com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.dto.response.SpatialElement;
import com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.dto.response.SpatialRecommendation;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StoreRouter {
    private final RadarEngine radarEngine;
    private final RecommendationEngine recommendationEngine;

    public StoreRouter(RadarEngine radarEngine,
                       RecommendationEngine recommendationEngine) {
        this.radarEngine = radarEngine;
        this.recommendationEngine = recommendationEngine;
    }

    public SpatialRecommendation recommendStoresNearby(String latlon, String storeType, String archetype) {
        // prepare for query
        SpatialQueryArchetype<FoodStoreResponse> queryArchetype = SpatialQueryArchetype.FoodStore.valueOf(archetype).get();
        String collectionName = Converter.toCollectionName(storeType);

        // query and data retrieval
        QueryResponse queryResponse = radarEngine.findWithinRadiusWithStats(collectionName, latlon, queryArchetype);
        Tuple<Double> distanceMinMax = ResponseUtil.findMinMaxFromStats(queryResponse, "geodist()"); // TODO refactor to use the archetype

        // data transformation
        List<FoodStoreResponse> responses = queryResponse.getBeans(FoodStoreResponse.class);
        List<SpatialElement> appraisedAndSortedResponses = responses.stream()
                .map(r -> {
                    Double normalizedRating = recommendationEngine.normalize(
                            r.getRating(), Rating.MIN_RATING_VALUE.doubleValue(), Rating.MAX_RATING_VALUE.doubleValue());

                    Double normalizedDistance = recommendationEngine.normalize(
                            r.getDistance(), distanceMinMax.right());

                    Double correlation = recommendationEngine.correlate(normalizedRating, 0.7, normalizedDistance, 0.3);

                    return new SpatialElement(correlation, r.getId(), r.getName(), r.getRating(), r.getDistance(), r.getLatitude(), r.getLongitude());
                })
                .sorted((r1, r2) -> r2.getAppraisal().compareTo(r1.getAppraisal()))
                .toList();

        return new SpatialRecommendation(appraisedAndSortedResponses.size(), distanceMinMax.right(), distanceMinMax.left(), appraisedAndSortedResponses);
    }
}