package com.gokdenizozkan.ddd.generalservice.feature.store.dto.response;

import com.gokdenizozkan.ddd.generalservice.feature.address.dto.response.AddressResponseMirror;

public record StoreDetailsWithFullAddress(
        Long id,
        String name,
        String email,
        String phone,
        Float storeRatingAverage,
        Long reviewCount,
        AddressResponseMirror address
) {
}
