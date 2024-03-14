package com.gokdenizozkan.ddd.generalservice.feature.user.buyer;

import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.request.BuyerSaveRequest;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.response.BuyerDetails;
import com.gokdenizozkan.ddd.generalservice.config.response.Structured;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buyers")
@Slf4j
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
    public ResponseEntity<Structured<BuyerDetails>> findById(@PathVariable Long id) {
        return responser.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Structured<BuyerDetails>> save(@RequestBody BuyerSaveRequest request) {
        log.info("Received request to save buyer: {}", request);
        return responser.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Structured<BuyerDetails>> update(@PathVariable Long id, @RequestBody BuyerSaveRequest request) {
        log.info("Received request to update buyer with id: {} and request: {}", id, request);
        return responser.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Structured<Object>> delete(@PathVariable Long id) {
        log.info("Received request to delete buyer with id: {}", id);
        return responser.delete(id);
    }
}