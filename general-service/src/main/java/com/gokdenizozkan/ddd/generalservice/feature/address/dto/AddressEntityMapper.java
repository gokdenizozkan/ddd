package com.gokdenizozkan.ddd.generalservice.feature.address.dto;

import com.gokdenizozkan.ddd.generalservice.feature.address.dto.mapper.AddressSaveRequestToAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressEntityMapper {
    public final AddressSaveRequestToAddress fromSaveRequest;
}
