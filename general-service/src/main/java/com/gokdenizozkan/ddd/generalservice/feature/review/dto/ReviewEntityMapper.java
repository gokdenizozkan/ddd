package com.gokdenizozkan.ddd.generalservice.feature.review.dto;

import com.gokdenizozkan.ddd.generalservice.feature.review.dto.mapper.ReviewSaveRequestToReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewEntityMapper {
    public final ReviewSaveRequestToReview fromSaveRequest;
}
