package com.gokdenizozkan.ddd.recommendationservice.entity.store.foodstore;

import com.gokdenizozkan.ddd.recommendationservice.entity.store.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.management.ConstructorParameters;

@Getter
@Setter
@ToString
public class FoodStore extends Store {

    public FoodStore(String id, String name, String latitude, String longitude, String latlon, Float rating) {
        super(id, name, latitude, longitude, latlon, rating);
    }
}
