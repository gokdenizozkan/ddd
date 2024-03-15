package com.gokdenizozkan.ddd.recommendationservice.feature.recommendation;

import com.gokdenizozkan.ddd.recommendationservice.core.datastructure.Tuple;
import com.gokdenizozkan.ddd.recommendationservice.core.spatial.SpatialQueryArchetype;
import com.gokdenizozkan.ddd.recommendationservice.core.util.Converter;
import com.gokdenizozkan.ddd.recommendationservice.core.util.ResponseUtil;
import com.gokdenizozkan.ddd.recommendationservice.entity.Rating;
import com.gokdenizozkan.ddd.recommendationservice.entity.store.foodstore.dto.response.FoodStoreResponse;
import com.gokdenizozkan.ddd.recommendationservice.feature.radar.RadarEngine;
import com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.dto.response.SpatialElement;
import com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.dto.response.SpatialRecommendation;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RecommendationRouter {
    private final RadarEngine radarEngine;
    private final RecommendationEngine recommendationEngine;

    public RecommendationRouter(RadarEngine radarEngine,
                                RecommendationEngine recommendationEngine) {
        this.radarEngine = radarEngine;
        this.recommendationEngine = recommendationEngine;
    }

    public SpatialRecommendation recommendStoresNearby(String latlon, String storeType, String archetype) {
        log.info("Preparing data, archetype in string is {}...", archetype);
        SpatialQueryArchetype<FoodStoreResponse> queryArchetype = SpatialQueryArchetype.FoodStore.valueOf(archetype).get();
        String collectionName = Converter.toCollectionName(storeType);
        log.info("Data was prepared: Collection name is {}, found queryArchetype is {}", collectionName, queryArchetype);

        log.info("Querying and retrieving store data...");
        QueryResponse queryResponse = radarEngine.findWithinRadiusWithStats(collectionName, latlon, queryArchetype);
        Tuple<Double> distanceMinMax = ResponseUtil.findMinMaxFromStats(queryResponse, "geodist()"); // TODO refactor to use the archetype
        log.info("Stores found within radius with stats: Distance min is {}, max is {}", distanceMinMax.left(), distanceMinMax.right());

        log.info("Normalizing and correlating store data...");
        List<FoodStoreResponse> responses = queryResponse.getBeans(FoodStoreResponse.class);
        if (responses.isEmpty()) {
            log.info("No store found, returning empty spatial recommendation.");
            return new SpatialRecommendation(0, distanceMinMax.right(), distanceMinMax.left(), List.of());
        }

        if (responses.size() == 1) {
            log.info("Found only one store, returning it directly.");
            FoodStoreResponse response = responses.getFirst();
            return new SpatialRecommendation(1, distanceMinMax.right(), distanceMinMax.left(), List.of(
                    new SpatialElement(1F, response.getId(), response.getName(), response.getRating(), response.getDistance(), response.getLatitude(), response.getLongitude())
            ));
        }

        List<SpatialElement> appraisedAndSortedResponses = responses.stream()
                .map(r -> {
                    Float normalizedRating = recommendationEngine.normalize(
                            r.getRating(), Rating.MIN_RATING_VALUE, Rating.MAX_RATING_VALUE);

                    Float normalizedDistance = recommendationEngine.normalize(
                            r.getDistance(), distanceMinMax.right().floatValue());

                    Float correlation = recommendationEngine.correlate(normalizedRating, 0.7F, normalizedDistance, 0.3F);
                    log.info("Correlation for store {} is {}; normalized rating was {} and normalized distance was {}", r.getName(), correlation, normalizedRating, normalizedDistance);

                    log.info("Returning spatial element.");
                    return new SpatialElement(correlation, r.getId(), r.getName(), r.getRating(), r.getDistance(), r.getLatitude(), r.getLongitude());
                })
                .sorted((r1, r2) -> r2.getAppraisal().compareTo(r1.getAppraisal()))
                .toList();

        log.info("Returning spatial recommendation from appraised and sorted responses.");
        return new SpatialRecommendation(appraisedAndSortedResponses.size(), distanceMinMax.right(), distanceMinMax.left(), appraisedAndSortedResponses);
    }
}