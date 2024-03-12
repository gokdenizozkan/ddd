package com.gokdenizozkan.ddd.generalservice.feature.address.dto;

import generalservice.feature.address.dto.mapper.AddressSaveRequestToAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressEntityMapper {
    public final AddressSaveRequestToAddress fromSaveRequest;
}
