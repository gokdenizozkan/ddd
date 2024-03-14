package com.gokdenizozkan.ddd.recommendationservice.entity;

public enum Rating {
    EXCELLENT(5F),
    SATISFACTORY(4F),
    DECENT(3F),
    LACKING(2F),
    POOR(1F);

    public final Float value;
    public static final Float MAX_RATING_VALUE = 5F;
    public static final Float MIN_RATING_VALUE = 1F;
    private final int availableRatingCount;

    Rating(Float value) {
        this.value = value;
        this.availableRatingCount = 5;
    }
}
