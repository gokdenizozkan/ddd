package com.gokdenizozkan.ddd.mainservice.feature.store.dto;

import com.gokdenizozkan.ddd.mainservice.feature.store.dto.mapper.StoreSaveRequestToStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreEntityMapper {
    public final StoreSaveRequestToStore fromSaveRequest;
}
