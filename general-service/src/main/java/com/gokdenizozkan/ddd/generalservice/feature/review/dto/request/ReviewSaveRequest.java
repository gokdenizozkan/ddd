package com.gokdenizozkan.ddd.generalservice.feature.review.dto.request;

import generalservice.feature.review.Rating;

public record ReviewSaveRequest(
        Rating rating,
        String experience,
        Long buyerId,
        Long storeId
) {
}
