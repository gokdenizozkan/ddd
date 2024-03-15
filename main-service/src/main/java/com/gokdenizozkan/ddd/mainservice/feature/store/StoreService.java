package com.gokdenizozkan.ddd.mainservice.feature.store;

import com.gokdenizozkan.ddd.mainservice.feature.store.dto.request.StoreSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.store.dto.response.StoreDetails;
import com.gokdenizozkan.ddd.mainservice.config.quality.BaseService;

import java.math.BigDecimal;
import java.util.List;

public interface StoreService extends BaseService<Store, Long, StoreSaveRequest> {
    @Override
    List<Store> findAll();

    @Override
    Store findById(Long id);

    @Override
    Store save(StoreSaveRequest request);

    @Override
    Store update(Long id, StoreSaveRequest request);

    @Override
    void delete(Long id);

    StoreDetails findStoreDetailsById(Long id);

    String updateStoreNameById(Long id, String name);

    String updateCoordinatesById(Long id, BigDecimal latitude, BigDecimal longitude);

    String updateCoordinatesById(Long id, String latitude, String longitude);
}