package com.gokdenizozkan.ddd.generalservice.feature.review.dto.response;

import generalservice.feature.review.Rating;

public record ReviewResponseMirror(
        Long id,
        Rating rating,
        String experience,
        Long buyerId,
        String buyerName,
        Long storeId,
        String storeName
) {
}