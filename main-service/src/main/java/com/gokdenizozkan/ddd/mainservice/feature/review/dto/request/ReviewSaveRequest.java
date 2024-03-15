package com.gokdenizozkan.ddd.mainservice.feature.review.dto.request;

import com.gokdenizozkan.ddd.mainservice.feature.review.Rating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReviewSaveRequest(
        @NotNull
        Rating rating,
        @NotBlank
        String experience,
        @NotNull @Positive
        Long buyerId,
        @NotNull @Positive
        Long storeId
) {
}
