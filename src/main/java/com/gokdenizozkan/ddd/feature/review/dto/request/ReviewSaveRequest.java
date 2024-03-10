package com.gokdenizozkan.ddd.feature.review.dto.request;

import com.gokdenizozkan.ddd.feature.review.Rating;

public record ReviewSaveRequest(
        Rating rating,
        String experience,
        Long buyerId,
        Long storeId
) {
}
