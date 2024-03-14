package com.gokdenizozkan.ddd.generalservice.feature.store.dto.mapper;

import com.gokdenizozkan.ddd.generalservice.feature.address.AddressRepository;
import com.gokdenizozkan.ddd.generalservice.feature.store.dto.request.StoreSaveRequest;
import com.gokdenizozkan.ddd.generalservice.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.generalservice.feature.address.Address;
import com.gokdenizozkan.ddd.generalservice.feature.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class StoreSaveRequestToStore implements Function<StoreSaveRequest, Store> {
    private final AddressRepository addressRepository;

    @Override
    public Store apply(StoreSaveRequest storeSaveRequest) {
        Store store = new Store();

        store.setName(storeSaveRequest.name());
        store.setEmail(storeSaveRequest.email());
        store.setPhone(storeSaveRequest.phone());
        store.setStoreType(storeSaveRequest.storeType());

        store.setAddress(addressRepository.findById(storeSaveRequest.addressId())
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Address.class, storeSaveRequest.addressId())));

        return store;
    }
}
