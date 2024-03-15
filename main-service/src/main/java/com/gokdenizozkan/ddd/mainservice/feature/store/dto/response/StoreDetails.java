package com.gokdenizozkan.ddd.mainservice.feature.store.dto.response;

import com.gokdenizozkan.ddd.mainservice.feature.store.StoreType;

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
