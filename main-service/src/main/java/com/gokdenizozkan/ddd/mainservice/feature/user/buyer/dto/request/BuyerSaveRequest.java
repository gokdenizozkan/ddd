package com.gokdenizozkan.ddd.mainservice.feature.user.buyer.dto.request;

import jakarta.annotation.Nullable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BuyerSaveRequest(
        @NotNull(message = "Name is required")
        String name,
        @Nullable
        String surname,
        @NotNull(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        @NotNull(message = "Phone number is required")
        @Pattern(regexp = "^[\\d()+\\s]*$", message = "Phone number can contain only digits, spaces and parentheses")
        @Size(min = 2, max = 20, message = "Phone number must be between 2 and 20 characters")
        String phone,
        @Nullable
        @Temporal(TemporalType.DATE)
        LocalDate birthdate
) {
}