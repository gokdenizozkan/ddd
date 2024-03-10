package com.gokdenizozkan.ddd.feature.store;

import com.gokdenizozkan.ddd.config.quality.BaseService;
import com.gokdenizozkan.ddd.feature.store.dto.request.StoreSaveRequest;
import com.gokdenizozkan.ddd.feature.store.dto.response.StoreDetails;

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
}