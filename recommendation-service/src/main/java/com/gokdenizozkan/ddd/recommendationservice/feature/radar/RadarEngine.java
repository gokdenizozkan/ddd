package com.gokdenizozkan.ddd.recommendationservice.feature.radar;

import com.gokdenizozkan.ddd.recommendationservice.core.quality.Spatial;
import com.gokdenizozkan.ddd.recommendationservice.core.spatial.SolrSpatialQuery;
import com.gokdenizozkan.ddd.recommendationservice.core.spatial.SpatialQueryArchetype;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;

public interface RadarEngine {
    default QueryResponse findWithinRadius(@NotBlank String collectionName, @NotBlank String query, @NotBlank String point,
                                          @NotBlank String latlonFieldName, @NotBlank Integer km,
                                          @Nullable SolrSpatialQuery.SortOrder sortOrder,
                                          @Nullable String fieldList,
                                          @Nullable Integer rows) {
        return SolrSpatialQuery.of(collectionName)
                .q(query)
                .point(point)
                .distance(km)
                .latlonFieldName(latlonFieldName)
                .filter(SolrSpatialQuery.FilterType.GEOFILT)
                .sort(SolrSpatialQuery.QueryFunction.GEODIST, sortOrder)
                .fieldList(fieldList)
                .rows(rows)
                .execute(getClient());
    }

    default <A extends Spatial<Float>> QueryResponse findWithinRadius(@NotBlank String collectionName, @NotBlank String point,
                                                               @NotNull SpatialQueryArchetype<A> archetype) {
        return findWithinRadius(collectionName, archetype.getQuery(), point, archetype.getLatlonFieldName(),
                archetype.getKm(), archetype.getSortOrder(), archetype.getFieldList(), archetype.getRows());
    }

    default <A extends Spatial<Float>, SRC> List<SRC> findWithinRadius(@NotBlank String collectionName, @NotBlank String point,
                                                               @NotNull SpatialQueryArchetype<A> archetype,
                                                               @NotNull Class<SRC> solrResponseClass) {
        return SolrSpatialQuery.of(collectionName)
                .q(archetype.getQuery())
                .point(point)
                .distance(archetype.getKm())
                .latlonFieldName(archetype.getLatlonFieldName())
                .filter(SolrSpatialQuery.FilterType.GEOFILT)
                .sort(SolrSpatialQuery.QueryFunction.GEODIST, archetype.getSortOrder())
                .fieldList(archetype.getFieldList())
                .rows(archetype.getRows())
                .executeAndGetAllAs(getClient(), solrResponseClass);
    }

    /**
     * Find within radius with stats.<br>
     * Stats are currently implemented for distance only.<br>
     * @param collectionName the name of the collection
     * @param query the query
     * @param point the point to calculate distance from
     * @param latlonFieldName name of the field that contains the comma-separated latlon values.
     * @param km the distance in kilometers
     * @param sortOrder the sort order
     * @param fieldList the field list of the response object
     * @param rows the number of rows to return
     * @return the query response
     */
    default QueryResponse findWithinRadiusWithStats(@NotBlank String collectionName, @NotBlank String query, @NotBlank String point,
                                           @NotBlank String latlonFieldName, @NotNull Integer km,
                                           @Nullable SolrSpatialQuery.SortOrder sortOrder,
                                           @Nullable String fieldList,
                                           @Nullable Integer rows) {

        return SolrSpatialQuery.of(collectionName)
                .q(query)
                .point(point)
                .distance(km)
                .latlonFieldName(latlonFieldName)
                .filter(SolrSpatialQuery.FilterType.GEOFILT)
                .sort(SolrSpatialQuery.QueryFunction.GEODIST, sortOrder)
                .fieldList(fieldList)
                .rows(rows)
                .stats(SolrSpatialQuery.QueryFunction.GEODIST.toString())
                .execute(getClient());
    }

    default <A extends Spatial<Float>> QueryResponse findWithinRadiusWithStats(@NotBlank String collectionName, @NotBlank String point,
                                                               @NotNull SpatialQueryArchetype<A> archetype) {
        return findWithinRadiusWithStats(collectionName, archetype.getQuery(), point, archetype.getLatlonFieldName(),
                archetype.getKm(), archetype.getSortOrder(), archetype.getFieldList(), archetype.getRows());
    }

    default <A extends Spatial<Float>, SRC> List<SRC> findWithinRadiusWithStats(@NotBlank String collectionName, @NotBlank String point,
                                                                @NotNull SpatialQueryArchetype<A> archetype,
                                                                @NotNull Class<SRC> solrResponseClass) {
        return SolrSpatialQuery.of(collectionName)
                .q(archetype.getQuery())
                .point(point)
                .distance(archetype.getKm())
                .latlonFieldName(archetype.getLatlonFieldName())
                .filter(SolrSpatialQuery.FilterType.GEOFILT)
                .sort(SolrSpatialQuery.QueryFunction.GEODIST, archetype.getSortOrder())
                .fieldList(archetype.getFieldList())
                .rows(archetype.getRows())
                .stats(SolrSpatialQuery.QueryFunction.GEODIST.toString())
                .executeAndGetAllAs(getClient(), solrResponseClass);
    }

    SolrClient getClient();
}
