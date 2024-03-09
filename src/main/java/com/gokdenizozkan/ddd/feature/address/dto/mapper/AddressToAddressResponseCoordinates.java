package com.gokdenizozkan.ddd.feature.address.dto.mapper;

import com.gokdenizozkan.ddd.feature.address.Address;
import com.gokdenizozkan.ddd.feature.address.dto.response.AddressResponseCoordinates;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddressToAddressResponseCoordinates implements Function<Address, AddressResponseCoordinates> {
    @Override
    public AddressResponseCoordinates apply(Address address) {
        return new AddressResponseCoordinates(
                address.getLatitude(),
                address.getLongitude()
        );
    }

}
