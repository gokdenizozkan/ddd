package com.gokdenizozkan.ddd.mainservice.feature.user.buyer.dto.response;

import java.time.LocalDate;

public record BuyerDetails(
        Long id,
        String name,
        String surname,
        String email,
        String phone,
        LocalDate birthdate
) {
}
