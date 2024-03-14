package com.gokdenizozkan.ddd.recommendationservice.feature.recommendation;

import org.springframework.stereotype.Service;

@Service
public interface RecommendationEngine {

    /**
     * Normalize a value to a range between 0 and 1
     *
     * @param value the value to normalize
     * @param min   the minimum value of the range
     * @param max   the maximum value of the range
     * @return the normalized value
     */
    default Double normalize(Double value, Double min, Double max) {
        return (value - min) / (max - min);
    }

    default Double normalize(Double distance, Double maxDistance) {
        return 1 - normalize(distance, 0.0, maxDistance);
    }

    default Double correlate(Double normalizedValueA, Double weightA, Double normalizedValueB, Double weightB) {
        return (normalizedValueA * weightA) + (normalizedValueB * weightB);
    }
}
