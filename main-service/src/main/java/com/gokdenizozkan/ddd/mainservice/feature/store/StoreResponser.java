package com.gokdenizozkan.ddd.mainservice.feature.store;

import com.gokdenizozkan.ddd.mainservice.feature.store.dto.StoreDtoMapper;
import com.gokdenizozkan.ddd.mainservice.feature.store.dto.request.StoreSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.store.dto.response.StoreDetails;
import com.gokdenizozkan.ddd.mainservice.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.mainservice.config.response.Structured;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
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
        log.info("Store Saved: {} - response to be created", store);
        StoreDetails response = dtoMapper.toDetails.apply(store);
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<StoreDetails>> update(Long id, StoreSaveRequest request) {
        Store store = service.update(id, request);
        log.info("Store Updated: {} - response to be created", store);
        StoreDetails response = dtoMapper.toDetails.apply(store);
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<Object>> delete(Long id) {
        service.delete(id);
        return ResponseTemplates.noContent();
    }

    public ResponseEntity<Structured<String>> updateCoordinatesById(Long id, String latitude, String longitude) {
        String response = service.updateCoordinatesById(id, latitude, longitude);
        log.info("Store Coordinates Updated: {} - response to be created", response);
        return ResponseTemplates.ok(response);
    }
}
