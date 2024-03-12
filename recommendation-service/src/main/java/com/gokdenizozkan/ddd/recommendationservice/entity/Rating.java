package com.gokdenizozkan.ddd.recommendationservice.entity;

public enum Rating {
    EXCELLENT(5),
    SATISFACTORY(4),
    DECENT(3),
    LACKING(2),
    POOR(1);

    public final int value;
    public static final Integer MAX_RATING_VALUE = 5;
    public static final Integer MIN_RATING_VALUE = 1;
    private final int availableRatingCount;

    Rating(int value) {
        this.value = value;
        this.availableRatingCount = 5;
    }
}
