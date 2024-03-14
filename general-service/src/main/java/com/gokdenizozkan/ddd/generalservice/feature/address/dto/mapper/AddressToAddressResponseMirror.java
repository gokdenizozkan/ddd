package com.gokdenizozkan.ddd.generalservice.feature.address.dto.mapper;

import com.gokdenizozkan.ddd.generalservice.feature.address.dto.response.AddressResponseMirror;
import com.gokdenizozkan.ddd.generalservice.feature.address.Address;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddressToAddressResponseMirror implements Function<Address, AddressResponseMirror> {
    @Override
    public AddressResponseMirror apply(Address address) {
        return new AddressResponseMirror(
                address.getId(),
                address.getLatitude(),
                address.getLongitude(),
                address.getCountryCode(),
                address.getCity(),
                address.getRegion(),
                address.getAddressLine(),
                address.getAddressDetails(),
                address.getContactPhone()
        );
    }
}
