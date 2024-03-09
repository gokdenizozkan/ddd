package com.gokdenizozkan.ddd.feature.address.dto.response;

import java.math.BigDecimal;

public record AddressResponseMirror (
        Long id,
        BigDecimal latitude,
        BigDecimal longitude,
        String countryCode,
        String city,
        String region,
        String addressLine,
        String addressDetails,
        String contactPhone
) {
}
