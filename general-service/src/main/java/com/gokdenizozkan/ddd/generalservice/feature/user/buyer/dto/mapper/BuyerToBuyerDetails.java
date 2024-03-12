package com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.mapper;

import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.Buyer;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.response.BuyerDetails;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BuyerToBuyerDetails implements Function<Buyer, BuyerDetails> {

    @Override
    public BuyerDetails apply(Buyer buyer) {
        return new BuyerDetails(
                buyer.getId(),
                buyer.getName(),
                buyer.getSurname(),
                buyer.getEmail(),
                buyer.getPhone(),
                buyer.getBirthdate()
        );
    }
}
