package com.gokdenizozkan.ddd.recommendationservice.core.spatial;

import com.gokdenizozkan.ddd.recommendationservice.core.quality.Spatial;
import com.gokdenizozkan.ddd.recommendationservice.core.quality.FieldStringifyable;
import com.gokdenizozkan.ddd.recommendationservice.entity.foodstore.dto.response.FoodStoreResponse;
import lombok.Getter;

@Getter
public class SpatialQueryArchetype<R extends Spatial> {
    private final String query;
    private final Integer km;
    private final String latlonFieldName;
    private final SolrSpatialQuery.SortOrder sortOrder;
    private final String fieldList;
    private final Integer rows;
    private final Class<R> solrResponseClass;

    private SpatialQueryArchetype(String query, Integer km, String latlonFieldName, SolrSpatialQuery.SortOrder sortOrder,
                                  String fieldList, Integer rows, Class<R> solrResponseClass) {
        this.query = query;
        this.km = km;
        this.latlonFieldName = latlonFieldName;
        this.sortOrder = sortOrder;
        this.fieldList = fieldList;
        this.rows = rows;
        this.solrResponseClass = solrResponseClass;
    }

    private SpatialQueryArchetype(String query, Integer km, String latlonFieldName, SolrSpatialQuery.SortOrder sortOrder,
                                  Class<? extends FieldStringifyable> fieldList, Integer rows, Class<R> solrResponseClass) {
        this.query = query;
        this.km = km;
        this.latlonFieldName = latlonFieldName;
        this.sortOrder = sortOrder;
        this.fieldList = FieldStringifyable.stringifyFields(fieldList);
        this.rows = rows;
        this.solrResponseClass = solrResponseClass;
    }

    public enum FoodStore {
        DEFAULT("*:*", 1000, "latlon", SolrSpatialQuery.SortOrder.ASC, "id,name,latlon,distance:geodist()", 25, FoodStoreResponse.class),
        ;

        private final SpatialQueryArchetype<FoodStoreResponse> archetype;

        public SpatialQueryArchetype<FoodStoreResponse> get() {
            return archetype;
        }

        FoodStore(String query, Integer km, String latlonFieldName, SolrSpatialQuery.SortOrder sortOrder,
                  String fieldList, Integer rows, Class<FoodStoreResponse> solrResponseClass) {
            this.archetype = new SpatialQueryArchetype<>(query, km, latlonFieldName, sortOrder, fieldList, rows, solrResponseClass);
        }

        FoodStore(String query, Integer km, String latlonFieldName, SolrSpatialQuery.SortOrder sortOrder,
                  Class<? extends FieldStringifyable> fieldList, Integer rows, Class<FoodStoreResponse> solrResponseClass) {
            this.archetype = new SpatialQueryArchetype<>(query, km, latlonFieldName, sortOrder, fieldList, rows, solrResponseClass);
        }
    }
}
