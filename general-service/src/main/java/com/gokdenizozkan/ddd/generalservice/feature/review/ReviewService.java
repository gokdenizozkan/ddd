package com.gokdenizozkan.ddd.generalservice.feature.review;

import com.gokdenizozkan.ddd.generalservice.config.quality.BaseService;
import com.gokdenizozkan.ddd.generalservice.feature.review.dto.request.ReviewSaveRequest;

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
}
