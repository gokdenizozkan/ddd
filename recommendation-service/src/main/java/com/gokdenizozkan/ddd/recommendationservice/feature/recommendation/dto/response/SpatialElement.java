package com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.dto.response;

import com.gokdenizozkan.ddd.recommendationservice.core.quality.Recommendable;
import com.gokdenizozkan.ddd.recommendationservice.core.quality.Spatial;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpatialElement implements Recommendable<Long>, Spatial<Float> {
    private final Float appraisal;
    private final Long entityId;
    private final String entityName;
    private final Float rating;
    private final Float distance;
    private final Float latitude;
    private final Float longitude;

    @Override
    public Float getAppraisal() {
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
    public Float getRating() {
        return rating;
    }

    @Override
    public Float getDistance() {
        return distance;
    }

    @Override
    public Float getLatitude() {
        return latitude;
    }

    @Override
    public Float getLongitude() {
        return longitude;
    }
}
