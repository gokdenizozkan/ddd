package com.gokdenizozkan.ddd.feature.review.dto.response;

import com.gokdenizozkan.ddd.feature.review.Rating;

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
