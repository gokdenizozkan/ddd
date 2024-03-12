package com.gokdenizozkan.ddd.generalservice.feature.address.dto.mapper;

import generalservice.feature.address.Address;
import generalservice.feature.address.dto.request.AddressSaveRequest;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddressSaveRequestToAddress implements Function<AddressSaveRequest, Address> {
    @Override
    public Address apply(AddressSaveRequest addressSaveRequest) {
        Address address = new Address();

        address.setLatitude(addressSaveRequest.latitude());
        address.setLongitude(addressSaveRequest.longitude());
        address.setCountryCode(addressSaveRequest.countryCode());
        address.setCity(addressSaveRequest.city());
        address.setRegion(addressSaveRequest.region());
        address.setAddressLine(addressSaveRequest.addressLine());
        address.setAddressDetails(addressSaveRequest.addressDetails());
        address.setContactPhone(addressSaveRequest.contactPhone());

        return address;
    }
}
