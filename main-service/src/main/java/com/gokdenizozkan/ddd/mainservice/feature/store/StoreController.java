package com.gokdenizozkan.ddd.mainservice.feature.store;

import com.gokdenizozkan.ddd.mainservice.feature.store.dto.request.StoreSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.store.dto.response.StoreDetails;
import com.gokdenizozkan.ddd.mainservice.config.response.Structured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
@Slf4j
@Validated
@Tag(name = "Store", description = "Store API")
public class StoreController {
    private final StoreResponser responser;

    public StoreController(StoreResponser responser) {
        this.responser = responser;
    }

    @GetMapping("/")
    @Operation(summary = "Find all stores", description = "Find all stores. Will only find active (enabled and non-deleted) entities.")
    public ResponseEntity<Structured<List<StoreDetails>>> findAll() {
        return responser.findAll();
    }

    @GetMapping("/{id}/details")
    @Operation(summary = "Find store details by id", description = "Find store details by id")
    public ResponseEntity<Structured<StoreDetails>> findStoreDetailsById(@PathVariable @Positive @NotNull Long id) {
        return responser.findStoreDetailsById(id);
    }

    @PostMapping("/")
    @Operation(summary = "Save store", description = "Save store. If Recommendation Service is offline or unable to process the sync request, save request will fail.")
    public ResponseEntity<Structured<StoreDetails>> save(@RequestBody @Valid StoreSaveRequest request) {
        log.info("Received save request with data: {}", request);
        return responser.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update store", description = "Update store. If Recommendation Service is offline or unable to process the sync request, update request will fail.")
    public ResponseEntity<Structured<StoreDetails>> update(@PathVariable @Positive @NotNull Long id, @RequestBody @Valid StoreSaveRequest request) {
        log.info("Received update request for id {} with data: {}", id, request);
        return responser.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete store", description = "Delete store. If Recommendation Service is offline or unable to process the sync request, delete request will fail.")
    public ResponseEntity<Structured<Object>> delete(@PathVariable @Positive @NotNull Long id) {
        log.info("Received delete request for id {}", id);
        return responser.delete(id);
    }

    @PatchMapping("/{id}/address/coordinates")
    @Operation(summary = "Update coordinates by id", description = "Update coordinates by id. If Recommendation Service is offline or unable to process the sync request, update request will fail.")
    public ResponseEntity<Structured<String>> updateCoordinatesById(@PathVariable @Positive @NotNull Long id,
                                                                   @RequestParam String latitude,
                                                                   @RequestParam String longitude) {
        log.info("Received update coordinates request for id {} with latitude: {} and longitude: {}", id, latitude, longitude);
        return responser.updateCoordinatesById(id, latitude, longitude);
    }
}