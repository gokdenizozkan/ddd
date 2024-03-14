package com.gokdenizozkan.ddd.recommendationservice.entity.store.foodstore.dto.response;

import com.gokdenizozkan.ddd.recommendationservice.core.quality.FieldStringifyable;
import com.gokdenizozkan.ddd.recommendationservice.core.quality.StoreResponse;
import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

import java.util.List;

@Getter
@Setter
public class FoodStoreResponse implements StoreResponse {
    @Field
    String id;
    @Field
    String name;
    @Field
    List<String> latlon;
    @Field
    Float distance;
    @Field
    Float rating;

    @Override
    public String stringifyFields() {
        return FieldStringifyable.stringifyFields(this.getClass());
    }

    @Override
    public Long getId() {
        return Long.parseLong(id);
    }

    @Override
    public Float getLatitude() {
        return Float.parseFloat(latlon.getFirst().split(",")[0]);
    }

    @Override
    public Float getLongitude() {
        return Float.parseFloat(latlon.getFirst().split(",")[1]);
    }
}