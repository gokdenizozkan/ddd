package com.gokdenizozkan.ddd.feature.review.dto;

import com.gokdenizozkan.ddd.feature.review.dto.mapper.ReviewSaveRequestToReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewEntityMapper {
    public final ReviewSaveRequestToReview fromSaveRequest;
}
