package com.gokdenizozkan.ddd.recommendationservice.entity.foodstore;

import com.gokdenizozkan.ddd.recommendationservice.core.quality.FieldStringifyable;
import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

import java.util.List;

@Getter
@Setter
public class SpatialResponse implements FieldStringifyable {
        @Field
        String id;
        @Field
        String name;
        @Field
        List<String> latlon;
        @Field
        Float distance;

        @Override
        public String stringifyFields() {
                return FieldStringifyable.stringifyFields(this.getClass());
        }
}
