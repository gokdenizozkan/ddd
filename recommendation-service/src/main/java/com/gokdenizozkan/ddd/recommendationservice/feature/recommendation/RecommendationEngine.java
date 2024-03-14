package com.gokdenizozkan.ddd.recommendationservice.feature.recommendation;

public interface RecommendationEngine {

    /**
     * Normalize a value to a range between 0 and 1
     *
     * @param value the value to normalize
     * @param min   the minimum value of the range
     * @param max   the maximum value of the range
     * @return the normalized value
     */
    default Float normalize(Float value, Float min, Float max) {
        return (value - min) / (max - min);
    }

    default Float normalize(Float distance, Float maxDistance) {
        return 1 - normalize(distance, 0.0F, maxDistance);
    }

    default Float correlate(Float normalizedValueA, Float weightA, Float normalizedValueB, Float weightB) {
        return (normalizedValueA * weightA) + (normalizedValueB * weightB);
    }
}
