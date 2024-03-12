package com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto;

import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.mapper.BuyerToBuyerDetails;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.mapper.BuyerToBuyerDetailsWithAddresses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuyerDtoMapper {
    public final BuyerToBuyerDetails toDetails;
    public final BuyerToBuyerDetailsWithAddresses toDetailsWithAddresses;
}
