package com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.dto.response;

import java.util.List;

/**
 *
 * @param recommendationAmount the amount of recommended entities
 * @param farthestDistance (in km) the farthest entity's distance from the requested point
 * @param closestDistance (in km) the closest entity's distance from the requested point
 * @param elements list of recommended entities
 */
public record SpatialRecommendation(
        Integer recommendationAmount,
        Double farthestDistance,
        Double closestDistance,
        List<SpatialElement> elements
) {
}
