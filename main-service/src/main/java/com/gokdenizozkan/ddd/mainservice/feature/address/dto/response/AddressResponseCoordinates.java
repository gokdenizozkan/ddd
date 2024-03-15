package com.gokdenizozkan.ddd.mainservice.feature.address.dto.response;

import java.math.BigDecimal;

/**
 * Address Response with coordinates only (latitude and longitude).
 * @param latitude *
 * @param longitude *
 */
public record AddressResponseCoordinates(
        BigDecimal latitude,
        BigDecimal longitude
) {
}
