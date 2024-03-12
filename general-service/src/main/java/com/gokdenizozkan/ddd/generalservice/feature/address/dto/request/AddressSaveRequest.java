package com.gokdenizozkan.ddd.generalservice.feature.address.dto.request;

import java.math.BigDecimal;

/**
 * Can be used for both save and update requests*.<br>
 * <br>
 * *For update requests, id field should be provided separately.
 *
 * @param latitude *
 * @param longitude *
 * @param countryCode *, in ALPHA-3 format (3 character long)
 * @param city *
 * @param region *
 * @param addressLine *
 * @param addressDetails
 * @param contactPhone *
 */
public record AddressSaveRequest(
        BigDecimal latitude,
        BigDecimal longitude,
        String countryCode,
        String city,
        String region,
        String addressLine,
        String addressDetails,
        String contactPhone) {
}