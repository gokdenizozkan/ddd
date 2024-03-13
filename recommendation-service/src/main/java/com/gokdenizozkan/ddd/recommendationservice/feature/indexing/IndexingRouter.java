package com.gokdenizozkan.ddd.recommendationservice.feature.indexing;

import com.gokdenizozkan.ddd.recommendationservice.core.util.Converter;
import com.gokdenizozkan.ddd.recommendationservice.entity.store.Store;
import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class IndexingRouter {
    private final IndexingEngine engine;

    public UpdateResponse indexStore(String storeType, String storeId, String latitude, String longitude, String name, Float rating) {
        String collectionName = Converter.toCollectionName(storeType);
        String latlon = latitude + "," + longitude;

        Store store = new Store(storeId, name, latitude, longitude, latlon, rating);
        return engine.index(collectionName, store);
    }

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

        engine.updateById(collectionName, storeId, fields);
    }

    public void updateStoreRating(String storeType, String storeId, Float rating) {
        String collectionName = Converter.toCollectionName(storeType);
        engine.updateById(collectionName, storeId, "rating", rating.toString());
    }

    public void updateStoreName(String storeType, String storeId, String name) {
        String collectionName = Converter.toCollectionName(storeType);
        engine.updateById(collectionName, storeId, "name", name);
    }

    public void updateStoreLatlon(String storeType, String storeId, String latitude, String longitude) {
        String collectionName = Converter.toCollectionName(storeType);
        String latlon = latitude + "," + longitude;

        Map<String, Object> fields = Map.of(
                "id", storeId,
                "latlon", latlon,
                "latitude", latitude,
                "longitude", longitude
        );

        engine.updateById(collectionName, storeId, fields);
    }
}
