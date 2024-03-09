package com.gokdenizozkan.ddd.feature.address.dto;

import com.gokdenizozkan.ddd.feature.address.dto.mapper.AddressToAddressResponseCoordinates;
import com.gokdenizozkan.ddd.feature.address.dto.mapper.AddressToAddressResponseMirror;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressDtoMapper {
    public final AddressToAddressResponseMirror toResponseMirror;
    public final AddressToAddressResponseCoordinates toResponseCoordinates;
}
