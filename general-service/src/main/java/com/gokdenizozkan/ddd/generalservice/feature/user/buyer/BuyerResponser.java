package com.gokdenizozkan.ddd.generalservice.feature.user.buyer;

import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.request.BuyerSaveRequest;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.response.BuyerDetails;
import com.gokdenizozkan.ddd.generalservice.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.generalservice.config.response.Structured;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.BuyerDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BuyerResponser {
    private final BuyerService service;
    private final BuyerDtoMapper dtoMapper;

    public BuyerResponser(@Qualifier("BuyerServiceActives") BuyerService service,
                          BuyerDtoMapper dtoMapper) {
        this.service = service;
        this.dtoMapper = dtoMapper;
    }

    public ResponseEntity<Structured<List<BuyerDetails>>> findAll() {
        List<Buyer> buyers = service.findAll();
        List<BuyerDetails> response = buyers.stream().map(dtoMapper.toDetails).toList();
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<BuyerDetails>> findById(Long id) {
        Buyer buyer = service.findById(id);
        BuyerDetails response = dtoMapper.toDetails.apply(buyer);
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<BuyerDetails>> save(BuyerSaveRequest request) {
        Buyer buyer = service.save(request);
        log.info("Buyer saved: {}", buyer);
        BuyerDetails response = dtoMapper.toDetails.apply(buyer);
        return ResponseTemplates.created(response);
    }

    public ResponseEntity<Structured<BuyerDetails>> update(Long id, BuyerSaveRequest request) {
        Buyer buyer = service.update(id, request);
        log.info("Buyer updated: {}", buyer);
        BuyerDetails response = dtoMapper.toDetails.apply(buyer);
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<Object>> delete(Long id) {
        service.delete(id);
        return ResponseTemplates.noContent();
    }
}