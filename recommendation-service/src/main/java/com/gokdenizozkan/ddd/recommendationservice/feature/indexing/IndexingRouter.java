package com.gokdenizozkan.ddd.recommendationservice.feature.indexing;

import com.gokdenizozkan.ddd.recommendationservice.core.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class IndexingRouter {
    private final IndexingEngine indexingEngine;

    public void updateStoreIndex(String storeType, String storeId, String latitude, String longitude, String name, Float rating) {
        String collectionName = Converter.toCollectionName(storeType);
        String latlon = latitude + "," + longitude;

        Map<String, Object> fields = Map.of(
                "id", storeId,
                "name", name,
                "latlon", latlon,
                "latitude", latitude,
                "longitude", longitude,
                "rating", rating
        );

        indexingEngine.updateById(collectionName, storeId, fields);
    }

    public void updateStoreRating(String storeType, String storeId, Float rating) {
        String collectionName = Converter.toCollectionName(storeType);
        indexingEngine.updateById(collectionName, storeId, "rating", rating.toString());
    }
}
