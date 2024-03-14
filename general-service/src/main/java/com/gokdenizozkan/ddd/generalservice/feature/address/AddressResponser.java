package com.gokdenizozkan.ddd.generalservice.feature.address;

import com.gokdenizozkan.ddd.generalservice.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.generalservice.config.response.Structured;
import com.gokdenizozkan.ddd.generalservice.feature.address.dto.AddressDtoMapper;
import com.gokdenizozkan.ddd.generalservice.feature.address.dto.request.AddressSaveRequest;
import com.gokdenizozkan.ddd.generalservice.feature.address.dto.response.AddressResponseCoordinates;
import com.gokdenizozkan.ddd.generalservice.feature.address.dto.response.AddressResponseMirror;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AddressResponser {
    private final AddressService service;
    private final AddressDtoMapper dtoMapper;

    public AddressResponser(@Qualifier("AddressServiceActives") AddressService service, AddressDtoMapper dtoMapper) {
        this.service = service;
        this.dtoMapper = dtoMapper;
    }

    public ResponseEntity<Structured<List<AddressResponseMirror>>> findAll() {
        List<Address> addresses = service.findAll();
        List<AddressResponseMirror> response = addresses.stream().map(dtoMapper.toResponseMirror).toList();
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<AddressResponseMirror>> findById(Long id) {
        Address address = service.findById(id);
        AddressResponseMirror response = dtoMapper.toResponseMirror.apply(address);
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<AddressResponseMirror>> save(AddressSaveRequest request) {
        Address address = service.save(request);
        log.info("Address Saved: {} - response to be created", address);
        AddressResponseMirror response = dtoMapper.toResponseMirror.apply(address);
        return ResponseTemplates.created(response);
    }

    public ResponseEntity<Structured<AddressResponseMirror>> update(Long id, AddressSaveRequest request) {
        Address address = service.update(id, request);
        log.info("Address Updated: {} - response to be created", address);
        AddressResponseMirror response = dtoMapper.toResponseMirror.apply(address);
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<Object>> delete(Long id) {
        service.delete(id);
        log.info("Address Deleted: {}", id);
        return ResponseTemplates.noContent();
    }

    public ResponseEntity<Structured<AddressResponseCoordinates>> findCoordinatesById(Long id) {
        AddressResponseCoordinates address = service.findCoordinatesById(id);
        return ResponseTemplates.ok(address);
    }
}
