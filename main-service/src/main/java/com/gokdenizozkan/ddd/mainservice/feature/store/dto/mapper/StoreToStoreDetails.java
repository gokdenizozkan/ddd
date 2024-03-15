package com.gokdenizozkan.ddd.mainservice.feature.store.dto.mapper;

import com.gokdenizozkan.ddd.mainservice.feature.store.dto.response.StoreDetails;
import com.gokdenizozkan.ddd.mainservice.feature.store.Store;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class StoreToStoreDetails implements Function<Store, StoreDetails> {
    @Override
    public StoreDetails apply(Store store) {
        return new StoreDetails(
                store.getId(),
                store.getName(),
                store.getEmail(),
                store.getPhone(),
                store.getStoreType(),
                store.getStoreRatingAverage(),
                store.getReviewCount()
        );
    }

}
