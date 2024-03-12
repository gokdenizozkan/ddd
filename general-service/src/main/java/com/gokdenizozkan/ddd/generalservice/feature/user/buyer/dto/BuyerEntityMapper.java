package com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto;

import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.mapper.BuyerSaveRequestToBuyer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuyerEntityMapper {
    public final BuyerSaveRequestToBuyer fromSaveRequest;
}
