package com.gokdenizozkan.ddd.mainservice.feature.store.dto;

import com.gokdenizozkan.ddd.mainservice.feature.store.dto.mapper.StoreToStoreDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreDtoMapper {
    public final StoreToStoreDetails toDetails;
}
