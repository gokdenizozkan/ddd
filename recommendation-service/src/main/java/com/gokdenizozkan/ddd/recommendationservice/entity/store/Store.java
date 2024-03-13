package com.gokdenizozkan.ddd.recommendationservice.entity.store;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.solr.client.solrj.beans.Field;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Store {
    @Field
    private String id;
    @Field
    private String name;
    @Field
    private BigDecimal latitude;
    @Field
    private BigDecimal longitude;
    @Field
    private String latlon;
}
