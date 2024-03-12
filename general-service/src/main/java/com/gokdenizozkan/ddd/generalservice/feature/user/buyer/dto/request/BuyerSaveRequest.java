package com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.request;

import java.time.LocalDate;

public record BuyerSaveRequest(
        String name,
        String surname,
        String email,
        String phone,
        LocalDate birthdate
) {
}