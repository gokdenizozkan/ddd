package com.gokdenizozkan.ddd.feature.user.buyer.dto.response;

import com.gokdenizozkan.ddd.feature.address.dto.response.AddressResponseMirror;

import java.time.LocalDate;
import java.util.List;

public record BuyerDetailsWithAddresses(
        Long id,
        String name,
        String surname,
        String email,
        String phone,
        LocalDate birthdate,
        List<AddressResponseMirror> addresses
) {
}
