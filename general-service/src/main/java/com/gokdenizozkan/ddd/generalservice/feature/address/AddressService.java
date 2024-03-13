package com.gokdenizozkan.ddd.generalservice.feature.address;

import com.gokdenizozkan.ddd.generalservice.config.quality.BaseService;
import com.gokdenizozkan.ddd.generalservice.feature.address.dto.request.AddressSaveRequest;
import com.gokdenizozkan.ddd.generalservice.feature.address.dto.response.AddressResponseCoordinates;

import java.util.List;


public interface AddressService extends BaseService<Address, Long, AddressSaveRequest> {
    @Override
    List<Address> findAll();

    @Override
    Address findById(Long id);

    @Override
    Address save(AddressSaveRequest request);

    @Override
    Address update(Long id, AddressSaveRequest request);

    @Override
    void delete(Long id);

    AddressResponseCoordinates findCoordinatesById(Long id);
}
