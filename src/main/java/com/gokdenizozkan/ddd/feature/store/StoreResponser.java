package com.gokdenizozkan.ddd.feature.store;

import com.gokdenizozkan.ddd.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.config.response.Structured;
import com.gokdenizozkan.ddd.feature.store.dto.StoreDtoMapper;
import com.gokdenizozkan.ddd.feature.store.dto.request.StoreSaveRequest;
import com.gokdenizozkan.ddd.feature.store.dto.response.StoreDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StoreResponser {
    private final StoreService service;
    private final StoreDtoMapper dtoMapper;

    public StoreResponser(@Qualifier("StoreServiceActives") StoreService service,
                          StoreDtoMapper dtoMapper) {
        this.service = service;
        this.dtoMapper = dtoMapper;
    }


    public ResponseEntity<Structured<List<StoreDetails>>> findAll() {
        List<Store> stores = service.findAll();
        List<StoreDetails> response = stores.stream().map(dtoMapper.toDetails).toList();
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<StoreDetails>> findStoreDetailsById(Long id) {
        StoreDetails storeDetails = service.findStoreDetailsById(id);
        return ResponseTemplates.ok(storeDetails);
    }

    public ResponseEntity<Structured<StoreDetails>> save(StoreSaveRequest request) {
        Store store = service.save(request);
        StoreDetails response = dtoMapper.toDetails.apply(store);
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<StoreDetails>> update(Long id, StoreSaveRequest request) {
        Store store = service.update(id, request);
        StoreDetails response = dtoMapper.toDetails.apply(store);
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<Object>> delete(Long id) {
        service.delete(id);
        return ResponseTemplates.noContent();
    }
}
