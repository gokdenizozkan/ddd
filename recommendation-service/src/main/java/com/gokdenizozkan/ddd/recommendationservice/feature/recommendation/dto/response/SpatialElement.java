package com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.dto.response;

import com.gokdenizozkan.ddd.recommendationservice.core.quality.Recommendable;
import com.gokdenizozkan.ddd.recommendationservice.core.quality.Spatial;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpatialElement implements Recommendable<Long>, Spatial<Double> {
    private final Double appraisal;
    private final Long entityId;
    private final String entityName;
    private final Double rating;
    private final Double distance;
    private final Double latitude;
    private final Double longitude;

    @Override
    public Double getAppraisal() {
        return appraisal;
    }

    @Override
    public Long getId() {
        return entityId;
    }

    @Override
    public String getName() {
        return entityName;
    }

    @Override
    public Double getRating() {
        return rating;
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public Double getLongitude() {
        return longitude;
    }
}
