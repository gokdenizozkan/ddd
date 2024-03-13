package com.gokdenizozkan.ddd.recommendationservice.entity.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.solr.client.solrj.beans.Field;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Store {
    @Field
    private String id;
    @Field
    private String name;
    @Field
    private String latitude;
    @Field
    private String longitude;
    @Field
    private String latlon;
    @Field
    private Float rating;
}
