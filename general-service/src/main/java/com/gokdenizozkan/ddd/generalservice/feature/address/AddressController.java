package com.gokdenizozkan.ddd.generalservice.feature.address;

import com.gokdenizozkan.ddd.generalservice.config.response.Structured;
import com.gokdenizozkan.ddd.generalservice.feature.address.dto.request.AddressSaveRequest;
import com.gokdenizozkan.ddd.generalservice.feature.address.dto.response.AddressResponseCoordinates;
import com.gokdenizozkan.ddd.generalservice.feature.address.dto.response.AddressResponseMirror;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@Slf4j
public class AddressController {
    private final AddressResponser responser;

    @GetMapping("/")
    public ResponseEntity<Structured<List<AddressResponseMirror>>> findAll() {
        return responser.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Structured<AddressResponseMirror>> findById(@PathVariable Long id) {
        return responser.findById(id);
    }

    @GetMapping("/{id}/coordinates")
    public ResponseEntity<Structured<AddressResponseCoordinates>> findCoordinatesById(@PathVariable Long id) {
        return responser.findCoordinatesById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Structured<AddressResponseMirror>> save(@RequestBody AddressSaveRequest request) {
        log.info("Received save request with data: {}", request);
        return responser.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Structured<AddressResponseMirror>> update(@PathVariable Long id, @RequestBody AddressSaveRequest request) {
        log.info("Received update request for id {} with data: {}", id, request);
        return responser.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Structured<Object>> delete(@PathVariable Long id) {
        log.info("Received delete request for id {}", id);
        return responser.delete(id);
    }
}
