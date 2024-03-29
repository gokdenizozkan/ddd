package com.gokdenizozkan.ddd.mainservice.feature.user.buyer;

import com.gokdenizozkan.ddd.mainservice.feature.user.buyer.dto.request.BuyerSaveRequest;
import com.gokdenizozkan.ddd.mainservice.config.quality.BaseService;

import java.util.List;

public interface BuyerService extends BaseService<Buyer, Long, BuyerSaveRequest> {
    @Override
    List<Buyer> findAll();

    @Override
    Buyer findById(Long id);

    @Override
    Buyer save(BuyerSaveRequest buyerSaveRequest);

    @Override
    Buyer update(Long id, BuyerSaveRequest buyerSaveRequest);

    @Override
    void delete(Long id);
}
