package com.gokdenizozkan.ddd.feature.address.dto;

import com.gokdenizozkan.ddd.feature.address.dto.mapper.AddressSaveRequestToAddress;
import com.gokdenizozkan.ddd.feature.address.dto.request.AddressSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressEntityMapper {
    public final AddressSaveRequestToAddress fromSaveRequest;
}
