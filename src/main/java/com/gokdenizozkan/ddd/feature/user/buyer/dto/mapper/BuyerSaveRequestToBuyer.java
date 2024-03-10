package com.gokdenizozkan.ddd.feature.user.buyer.dto.mapper;

import com.gokdenizozkan.ddd.feature.user.buyer.Buyer;
import com.gokdenizozkan.ddd.feature.user.buyer.dto.request.BuyerSaveRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.function.Function;

@Component
public class BuyerSaveRequestToBuyer implements Function<BuyerSaveRequest, Buyer> {
        @Override
        public Buyer apply(BuyerSaveRequest request) {
            Buyer buyer = new Buyer();

            buyer.setName(request.name());
            buyer.setSurname(request.surname());
            buyer.setEmail(request.email());
            buyer.setPhone(request.phone());
            buyer.setBirthdate(request.birthdate());

            buyer.setAddresses(new ArrayList<>());
            buyer.setReviews(new ArrayList<>());

            return buyer;
        }
}
