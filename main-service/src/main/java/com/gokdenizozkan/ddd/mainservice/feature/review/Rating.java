package com.gokdenizozkan.ddd.mainservice.feature.review;

import lombok.Getter;

@Getter
public enum Rating {
    EXCELLENT(5F),
    SATISFACTORY(4F),
    DECENT(3F),
    LACKING(2F),
    POOR(1F);

    private final Float value;
    private final int availableRatingCount;

    Rating(Float value) {
        this.value = value;
        this.availableRatingCount = 5;
    }
}
