package com.gokdenizozkan.ddd.recommendationservice.feature.recommendation;

import com.gokdenizozkan.ddd.recommendationservice.core.SolrSpatialQuery;
import com.gokdenizozkan.ddd.recommendationservice.core.quality.FieldStringifyable;
import com.gokdenizozkan.ddd.recommendationservice.entity.foodstore.SpatialResponse;
import lombok.Getter;

@Getter
public enum SpatialQueryArchetype {
    DEFAULT("*:*",
            10,
            "latlon",
            SolrSpatialQuery.SortOrder.ASC,
            SpatialResponse.class,
            10),;

    private final String query;
    private final Integer km;
    private final String latlonFieldName;
    private final SolrSpatialQuery.SortOrder sortOrder;
    private final String fieldList;
    private final Integer rows;

    SpatialQueryArchetype(
            String query,
            Integer km,
            String latlonFieldName,
            SolrSpatialQuery.SortOrder sortOrder,
            String fieldList,
            Integer rows
    ) {
        this.query = query;
        this.km = km;
        this.latlonFieldName = latlonFieldName;
        this.sortOrder = sortOrder;
        this.fieldList = fieldList;
        this.rows = rows;
    }

    SpatialQueryArchetype(
            String query,
            Integer km,
            String latlonFieldName,
            SolrSpatialQuery.SortOrder sortOrder,
            Class<? extends FieldStringifyable> fieldList,
            Integer rows
    ) {
        this.query = query;
        this.km = km;
        this.latlonFieldName = latlonFieldName;
        this.sortOrder = sortOrder;
        this.fieldList = FieldStringifyable.stringifyFields(fieldList);
        this.rows = rows;
    }
}
