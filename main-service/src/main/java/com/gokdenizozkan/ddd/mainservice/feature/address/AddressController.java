package com.gokdenizozkan.ddd.mainservice.feature.address;

import com.gokdenizozkan.ddd.mainservice.config.response.Structured;
import com.gokdenizozkan.ddd.mainservice.feature.address.dto.request.AddressSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.address.dto.response.AddressResponseCoordinates;
import com.gokdenizozkan.ddd.mainservice.feature.address.dto.response.AddressResponseMirror;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
@Tag(name = "Address", description = "Address API")
public class AddressController {
    private final AddressResponser responser;

    @GetMapping("/")
    @Operation(summary = "Find all addresses", description = "Find all addresses. Will only find active (enabled and non-deleted) entities.")
    public ResponseEntity<Structured<List<AddressResponseMirror>>> findAll() {
        return responser.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find address by id", description = "Find address by id")
    public ResponseEntity<Structured<AddressResponseMirror>> findById(@PathVariable @Positive @NotNull Long id) {
        return responser.findById(id);
    }

    @GetMapping("/{id}/coordinates")
    @Operation(summary = "Find coordinates by id", description = "Find coordinates by id")
    public ResponseEntity<Structured<AddressResponseCoordinates>> findCoordinatesById(@PathVariable @Positive @NotNull Long id) {
        return responser.findCoordinatesById(id);
    }

    @PostMapping("/")
    @Operation(summary = "Save address", description = "Save address")
    public ResponseEntity<Structured<AddressResponseMirror>> save(@RequestBody @Valid AddressSaveRequest request) {
        log.info("Received save request with data: {}", request);
        return responser.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update address", description = "Updating addresses directly is not advised, for store addresses in particular. To update addresses, use the corresponding entity's own update address endpoint.")
    public ResponseEntity<Structured<AddressResponseMirror>> update(@PathVariable @Positive @NotNull Long id, @RequestBody @Valid AddressSaveRequest request) {
        log.info("Received update request for id {} with data: {}", id, request);
        return responser.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete address", description = "Soft delete address. To truly delete the data, contact the system administrator.")
    public ResponseEntity<Structured<Object>> delete(@PathVariable @Positive @NotNull Long id) {
        log.info("Received delete request for id {}", id);
        return responser.delete(id);
    }
}
