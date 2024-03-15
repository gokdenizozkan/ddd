package com.gokdenizozkan.ddd.mainservice.feature.address;

import com.gokdenizozkan.ddd.mainservice.config.quality.BaseService;
import com.gokdenizozkan.ddd.mainservice.feature.address.dto.request.AddressSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.address.dto.response.AddressResponseCoordinates;

import java.math.BigDecimal;
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

    String updateCoordinatesById(Long id, BigDecimal latitude, BigDecimal longitude);

    String updateCoordinatesById(Long id, String latitude, String longitude);
}
