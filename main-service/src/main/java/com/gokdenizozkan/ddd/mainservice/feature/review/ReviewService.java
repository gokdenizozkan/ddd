package com.gokdenizozkan.ddd.mainservice.feature.review;

import com.gokdenizozkan.ddd.mainservice.config.quality.BaseService;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.request.ReviewSaveRequest;

import java.util.List;

public interface ReviewService extends BaseService<Review, Long, ReviewSaveRequest> {
    @Override
    List<Review> findAll();

    @Override
    Review findById(Long id);

    @Override
    Review save(ReviewSaveRequest reviewSaveRequest);

    @Override
    Review update(Long id, ReviewSaveRequest reviewSaveRequest);

    @Override
    void delete(Long id);

    void patch(Long id, String experience, String ratingString);
}
