package com.gokdenizozkan.ddd.recommendationservice.entity.store.foodstore;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    @Field
    private Long id;
    @Field
    private String name;
    @Field
    private String description;
    @Field
    private Long price;
    @Field
    private String currency;
    @Field
    private Long storeId;
    @Field
    private String storeName;
}
