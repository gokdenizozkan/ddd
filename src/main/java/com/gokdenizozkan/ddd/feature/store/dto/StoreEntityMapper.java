package com.gokdenizozkan.ddd.feature.store.dto;

import com.gokdenizozkan.ddd.feature.store.dto.mapper.StoreSaveRequestToStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreEntityMapper {
    public final StoreSaveRequestToStore fromSaveRequest;
}
