package com.gokdenizozkan.ddd.generalservice.feature.review.dto.request;

import com.gokdenizozkan.ddd.generalservice.feature.review.Rating;

public record ReviewSaveRequest(
        Rating rating,
        String experience,
        Long buyerId,
        Long storeId
) {
}
