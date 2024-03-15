package com.gokdenizozkan.ddd.mainservice.feature.user.buyer;

import com.gokdenizozkan.ddd.mainservice.feature.user.buyer.dto.request.BuyerSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.user.buyer.dto.response.BuyerDetails;
import com.gokdenizozkan.ddd.mainservice.config.response.Structured;
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
public class BuyerController {
    private final BuyerResponser responser;

    public BuyerController(BuyerResponser responser) {
        this.responser = responser;
    }

    @GetMapping("/")
    public ResponseEntity<Structured<List<BuyerDetails>>> findAll() {
        return responser.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Structured<BuyerDetails>> findById(@PathVariable @Positive @NotNull Long id) {
        return responser.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Structured<BuyerDetails>> save(@RequestBody @Valid BuyerSaveRequest request) {
        log.info("Received request to save buyer: {}", request);
        return responser.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Structured<BuyerDetails>> update(@PathVariable @Positive Long id, @RequestBody @Valid BuyerSaveRequest request) {
        log.info("Received request to update buyer with id: {} and request: {}", id, request);
        return responser.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Structured<Object>> delete(@PathVariable @Positive @NotNull Long id) {
        log.info("Received request to delete buyer with id: {}", id);
        return responser.delete(id);
    }
}