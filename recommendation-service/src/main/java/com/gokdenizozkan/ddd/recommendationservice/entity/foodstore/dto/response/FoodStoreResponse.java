package com.gokdenizozkan.ddd.recommendationservice.entity.foodstore.dto.response;

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
    Double distance;
    @Field
    Double rating;

    @Override
    public String stringifyFields() {
        return FieldStringifyable.stringifyFields(this.getClass());
    }

    @Override
    public Long getId() {
        return Long.parseLong(id);
    }

    @Override
    public Double getLatitude() {
        return Double.parseDouble(latlon.getFirst().split(",")[0]);
    }

    @Override
    public Double getLongitude() {
        return Double.parseDouble(latlon.getFirst().split(",")[1]);
    }
}