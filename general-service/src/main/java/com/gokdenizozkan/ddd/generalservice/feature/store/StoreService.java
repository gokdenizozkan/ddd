package com.gokdenizozkan.ddd.generalservice.feature.store;

import com.gokdenizozkan.ddd.generalservice.feature.store.dto.request.StoreSaveRequest;
import com.gokdenizozkan.ddd.generalservice.feature.store.dto.response.StoreDetails;
import com.gokdenizozkan.ddd.generalservice.config.quality.BaseService;

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
}