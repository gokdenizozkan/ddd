package com.gokdenizozkan.ddd.generalservice.feature.store;

import com.gokdenizozkan.ddd.generalservice.feature.store.dto.request.StoreSaveRequest;
import com.gokdenizozkan.ddd.generalservice.feature.store.dto.response.StoreDetails;
import com.gokdenizozkan.ddd.generalservice.config.response.Structured;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
@Slf4j
public class StoreController {
    private final StoreResponser responser;

    public StoreController(StoreResponser responser) {
        this.responser = responser;
    }

    @GetMapping("/")
    public ResponseEntity<Structured<List<StoreDetails>>> findAll() {
        return responser.findAll();
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<Structured<StoreDetails>> findStoreDetailsById(@PathVariable Long id) {
        return responser.findStoreDetailsById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Structured<StoreDetails>> save(@RequestBody StoreSaveRequest request) {
        log.info("Received save request with data: {}", request);
        return responser.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Structured<StoreDetails>> update(@PathVariable Long id, @RequestBody StoreSaveRequest request) {
        log.info("Received update request for id {} with data: {}", id, request);
        return responser.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Structured<Object>> delete(@PathVariable Long id) {
        log.info("Received delete request for id {}", id);
        return responser.delete(id);
    }

    @PatchMapping("/{id}/address/coordinates")
    public ResponseEntity<Structured<String>> updateCoordinatesById(@PathVariable Long id,
                                                                   @RequestParam String latitude,
                                                                   @RequestParam String longitude) {
        log.info("Received update coordinates request for id {} with latitude: {} and longitude: {}", id, latitude, longitude);
        return responser.updateCoordinatesById(id, latitude, longitude);
    }
}