package com.gokdenizozkan.ddd.generalservice.feature.store.dto.mapper;

import com.gokdenizozkan.ddd.generalservice.feature.address.AddressRepository;
import com.gokdenizozkan.ddd.generalservice.feature.store.dto.request.StoreSaveRequest;
import com.gokdenizozkan.ddd.generalservice.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.generalservice.feature.address.Address;
import com.gokdenizozkan.ddd.generalservice.feature.legalentity.LegalEntity;
import com.gokdenizozkan.ddd.generalservice.feature.legalentity.LegalEntityRepository;
import com.gokdenizozkan.ddd.generalservice.feature.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class StoreSaveRequestToStore implements Function<StoreSaveRequest, Store> {
    private final LegalEntityRepository legalEntityRepository;
    private final AddressRepository addressRepository;

    @Override
    public Store apply(StoreSaveRequest storeSaveRequest) {
        Store store = new Store();

        store.setName(storeSaveRequest.name());
        store.setEmail(storeSaveRequest.email());
        store.setPhone(storeSaveRequest.phone());
        store.setStoreType(storeSaveRequest.storeType());

        store.setLegalEntity(legalEntityRepository.mockFindById(storeSaveRequest.legalEntityId())
                .orElseThrow(() -> new ResourceNotFoundWithIdException(LegalEntity.class, storeSaveRequest.legalEntityId())));

        store.setAddress(addressRepository.findById(storeSaveRequest.addressId())
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Address.class, storeSaveRequest.addressId())));

        store.setStoreRatingAverage(0F);
        store.setReviewCount(0L);
        store.setReviews(new ArrayList<>());

        return store;
    }
}
