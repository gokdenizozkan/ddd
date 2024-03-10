package com.gokdenizozkan.ddd.feature.user.buyer.dto.mapper;

import com.gokdenizozkan.ddd.feature.address.dto.AddressDtoMapper;
import com.gokdenizozkan.ddd.feature.user.buyer.Buyer;
import com.gokdenizozkan.ddd.feature.user.buyer.dto.response.BuyerDetailsWithAddresses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BuyerToBuyerDetailsWithAddresses implements Function<Buyer, BuyerDetailsWithAddresses> {
    private final AddressDtoMapper addressDtoMapper;

    @Override
    public BuyerDetailsWithAddresses apply(Buyer buyer) {
        return new BuyerDetailsWithAddresses(
                buyer.getId(),
                buyer.getName(),
                buyer.getSurname(),
                buyer.getEmail(),
                buyer.getPhone(),
                buyer.getBirthdate(),
                buyer.getAddresses().stream().map(addressDtoMapper.toResponseMirror).toList()
        );
    }
}
