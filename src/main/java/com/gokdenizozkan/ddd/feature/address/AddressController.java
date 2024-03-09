package com.gokdenizozkan.ddd.feature.address;

import com.gokdenizozkan.ddd.config.response.Structured;
import com.gokdenizozkan.ddd.core.datastructure.Tuple;
import com.gokdenizozkan.ddd.feature.address.dto.request.AddressSaveRequest;
import com.gokdenizozkan.ddd.feature.address.dto.response.AddressResponseMirror;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/")
    public ResponseEntity<Structured<AddressResponseMirror>> save(@RequestBody AddressSaveRequest request) {
        ResponseEntity<Structured<AddressResponseMirror>> saved = responser.save(request);
        return saved;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Structured<AddressResponseMirror>> update(@PathVariable Long id, @RequestBody AddressSaveRequest request) {
        return responser.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Structured<Object>> delete(@PathVariable Long id) {
        return responser.delete(id);
    }
}
