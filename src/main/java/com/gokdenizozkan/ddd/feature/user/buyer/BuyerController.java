package com.gokdenizozkan.ddd.feature.user.buyer;

import com.gokdenizozkan.ddd.config.response.Structured;
import com.gokdenizozkan.ddd.feature.user.buyer.dto.request.BuyerSaveRequest;
import com.gokdenizozkan.ddd.feature.user.buyer.dto.response.BuyerDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buyers")
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
        return responser.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Structured<BuyerDetails>> update(@PathVariable Long id, @RequestBody BuyerSaveRequest request) {
        return responser.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Structured<Object>> delete(@PathVariable Long id) {
        return responser.delete(id);
    }
}