package com.gokdenizozkan.ddd.mainservice.feature.review.dto;

import com.gokdenizozkan.ddd.mainservice.feature.review.dto.mapper.ReviewToReviewResponseMirror;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewDtoMapper {
    public final ReviewToReviewResponseMirror toResponseMirror;
}
