package com.gokdenizozkan.ddd.mainservice.feature.address.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * Can be used for both save and update requests*.<br>
 * <br>
 * *For update requests, id field should be provided separately.
 *
 * @param latitude       *
 * @param longitude      *
 * @param countryCode    *, in ALPHA-3 format (3 character long)
 * @param city           *
 * @param region         *
 * @param addressLine    *
 * @param addressDetails
 * @param contactPhone   *
 */
@Builder
public record AddressSaveRequest(
        @NotNull
        BigDecimal latitude,
        @NotNull
        BigDecimal longitude,
        @Size(min = 3, max = 3, message = "Country code must be 3 characters (ALPHA-3 CODE)")
        String countryCode,
        @NotNull
        String city,
        @NotNull
        String region,
        @NotNull
        String addressLine,
        @Nullable
        String addressDetails,
        @NotNull(message = "Phone number is required")
        @Pattern(regexp = "^[\\d()+\\s]*$", message = "Phone number can contain only digits, spaces and parentheses")
        @Size(min = 2, max = 20, message = "Phone number must be between 2 and 20 characters")
        String contactPhone) {
}