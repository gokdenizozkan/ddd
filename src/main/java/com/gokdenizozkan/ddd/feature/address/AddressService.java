package com.gokdenizozkan.ddd.feature.address;

import com.gokdenizozkan.ddd.config.quality.BaseServiceWithSpecifications;
import com.gokdenizozkan.ddd.core.datastructure.Tuple;
import com.gokdenizozkan.ddd.feature.address.dto.request.AddressSaveRequest;

import java.util.List;


public interface AddressService extends BaseServiceWithSpecifications<Address, Long, AddressSaveRequest> {
    List<Address> findAll();

    Address findById(Long id);

    Address save(AddressSaveRequest request);

    Tuple<Address> update(Long id, AddressSaveRequest request);

    void delete(Long id);
}
