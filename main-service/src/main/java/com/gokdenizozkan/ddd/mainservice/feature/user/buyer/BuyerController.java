package com.gokdenizozkan.ddd.mainservice.feature.user.buyer;

import com.gokdenizozkan.ddd.mainservice.feature.user.buyer.dto.request.BuyerSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.user.buyer.dto.response.BuyerDetails;
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
@RequestMapping("/api/v1/buyers")
@Slf4j
@Validated
@Tag(name = "Buyer", description = "Buyer API")
public class BuyerController {
    private final BuyerResponser responser;

    public BuyerController(BuyerResponser responser) {
        this.responser = responser;
    }

    @GetMapping("/")
    @Operation(summary = "Find all buyers", description = "Find all buyers. Will only find active (enabled and non-deleted) entities.")
    public ResponseEntity<Structured<List<BuyerDetails>>> findAll() {
        return responser.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find buyer by id", description = "Find buyer by id.")
    public ResponseEntity<Structured<BuyerDetails>> findById(@PathVariable @Positive @NotNull Long id) {
        return responser.findById(id);
    }

    @PostMapping("/")
    @Operation(summary = "Save buyer", description = "Save buyer")
    public ResponseEntity<Structured<BuyerDetails>> save(@RequestBody @Valid BuyerSaveRequest request) {
        log.info("Received request to save buyer: {}", request);
        return responser.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update buyer", description = "Update buyer")
    public ResponseEntity<Structured<BuyerDetails>> update(@PathVariable @Positive Long id, @RequestBody @Valid BuyerSaveRequest request) {
        log.info("Received request to update buyer with id: {} and request: {}", id, request);
        return responser.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete buyer", description = "Delete buyer")
    public ResponseEntity<Structured<Object>> delete(@PathVariable @Positive @NotNull Long id) {
        log.info("Received request to delete buyer with id: {}", id);
        return responser.delete(id);
    }
}