package com.gokdenizozkan.ddd.feature.user.buyer.dto.response;

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
