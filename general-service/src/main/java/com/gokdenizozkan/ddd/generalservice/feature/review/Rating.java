package com.gokdenizozkan.ddd.generalservice.feature.review;

public enum Rating {
    EXCELLENT(5),
    SATISFACTORY(4),
    DECENT(3),
    LACKING(2),
    POOR(1);

    public final int value;
    private final int availableRatingCount;

    Rating(int value) {
        this.value = value;
        this.availableRatingCount = 5;
    }

    public double normalized() {
        return (double) value / availableRatingCount;
    }
}
