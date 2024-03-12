package com.gokdenizozkan.ddd.recommendationservice.core.quality;

public interface Recommendable<ID> extends Appraisable<Double>, IdHolder<ID>, NameHolder, RatingHolder<Double> {
}
