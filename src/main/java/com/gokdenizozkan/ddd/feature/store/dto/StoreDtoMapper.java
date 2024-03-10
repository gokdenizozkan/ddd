package com.gokdenizozkan.ddd.feature.store.dto;

import com.gokdenizozkan.ddd.feature.store.dto.mapper.StoreToStoreDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreDtoMapper {
    public final StoreToStoreDetails toDetails;
}
