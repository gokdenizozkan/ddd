package com.gokdenizozkan.ddd.mainservice.feature.review.dto.response;

import com.gokdenizozkan.ddd.mainservice.feature.review.Rating;

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
