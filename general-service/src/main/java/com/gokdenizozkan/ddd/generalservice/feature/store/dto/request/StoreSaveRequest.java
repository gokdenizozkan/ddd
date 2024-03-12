package com.gokdenizozkan.ddd.generalservice.feature.store.dto.request;

import com.gokdenizozkan.ddd.generalservice.feature.store.StoreType;

public record StoreSaveRequest(
        String name,
        String email,
        String phone,
        StoreType storeType,
        Long legalEntityId,
        Long addressId
) {
}
