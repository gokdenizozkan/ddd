package com.gokdenizozkan.ddd.generalservice.feature.store.dto.response;

import com.gokdenizozkan.ddd.generalservice.feature.store.StoreType;

public record StoreDetails(
        Long id,
        String name,
        String email,
        String phone,
        StoreType storeType,
        Float storeRatingAverage,
        Long reviewCount
) {
}
