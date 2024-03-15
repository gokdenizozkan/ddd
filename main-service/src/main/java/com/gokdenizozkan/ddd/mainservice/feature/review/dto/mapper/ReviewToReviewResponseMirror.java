package com.gokdenizozkan.ddd.mainservice.feature.review.dto.mapper;

import com.gokdenizozkan.ddd.mainservice.feature.review.Review;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.response.ReviewResponseMirror;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ReviewToReviewResponseMirror implements Function<Review, ReviewResponseMirror>{

    @Override
    public ReviewResponseMirror apply(Review review) {
        return new ReviewResponseMirror(
                review.getId(),
                review.getRating(),
                review.getExperience(),
                review.getBuyer().getId(),
                review.getBuyer().getName(),
                review.getStore().getId(),
                review.getStore().getName()
        );
    }
}
