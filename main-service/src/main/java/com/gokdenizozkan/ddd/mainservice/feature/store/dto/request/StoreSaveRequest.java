package com.gokdenizozkan.ddd.mainservice.feature.store.dto.request;

import com.gokdenizozkan.ddd.mainservice.feature.store.StoreType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record StoreSaveRequest(
        @NotNull(message = "Name is required")
        String name,
        @NotNull(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        @NotNull(message = "Phone number is required")
        @Pattern(regexp = "^[\\d()+\\s]*$", message = "Phone number can contain only digits, spaces and parentheses")
        @Size(min = 2, max = 20, message = "Phone number must be between 2 and 20 characters")
        String phone,
        @NotNull(message = "Store type is required")
        StoreType storeType,
        @NotNull @Positive
        Long addressId
) {
}
