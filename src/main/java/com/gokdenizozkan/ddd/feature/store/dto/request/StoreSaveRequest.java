package com.gokdenizozkan.ddd.feature.store.dto.request;

import com.gokdenizozkan.ddd.feature.store.StoreType;

public record StoreSaveRequest(
        String name,
        String email,
        String phone,
        StoreType storeType,
        Long legalEntityId,
        Long addressId
) {
}
