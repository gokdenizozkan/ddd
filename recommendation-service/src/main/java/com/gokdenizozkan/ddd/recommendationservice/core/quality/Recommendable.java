package com.gokdenizozkan.ddd.recommendationservice.core.quality;

public interface Recommendable<ID> extends Appraisable<Float>, IdHolder<ID>, NameHolder, RatingHolder<Float> {
}
