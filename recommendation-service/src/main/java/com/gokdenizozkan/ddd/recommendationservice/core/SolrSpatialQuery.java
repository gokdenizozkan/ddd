package com.gokdenizozkan.ddd.recommendationservice.core;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SpatialParams;

import java.util.List;

public class SolrSpatialQuery {
    private String q;
    private String collectionName;
    private String point;
    private String latlonFieldName;
    private int km;
    private int rows;
    private FilterType filterType;
    private SortType sortType;
    private SortOrder sortOrder;
    private String fieldList;
    private boolean spatial;
    private SolrQuery query;


    private SolrSpatialQuery(String collectionName) {
        this.collectionName = collectionName;
        this.query = new SolrQuery();
        this.spatial = true;
    }

    public static SolrSpatialQuery of(String collectionName) {
        return new SolrSpatialQuery(collectionName);
    }

    public SolrSpatialQuery q(String q) {
        this.q = q;
        query.setQuery(q);
        return this;
    }

    public SolrSpatialQuery point(String point) {
        this.point = point;
        query.set(SpatialParams.POINT, point);
        return this;
    }

    public SolrSpatialQuery latlonFieldName(String latlonFieldName) {
        this.latlonFieldName = latlonFieldName;
        query.set(SpatialParams.FIELD, latlonFieldName);
        return this;
    }

    public SolrSpatialQuery distance(int km) {
        this.km = km;
        query.set(SpatialParams.DISTANCE, km);
        return this;
    }

    public SolrSpatialQuery rows(int rows) {
        this.rows = rows;
        query.set("rows", String.valueOf(rows));
        return this;
    }

    /**
     * If filter type is selected NO_FILTER, response will only contain "score" local parameter.<br>
     */
    public SolrSpatialQuery filter(FilterType filterType) {
        this.filterType = filterType;
        query.addFilterQuery(filterType.toString());
        return this;
    }

    public SolrSpatialQuery sort(SortType sortType, SortOrder sortOrder) {
        this.sortType = sortType;
        this.sortOrder = sortOrder;
        query.set("sort", String.format("%s %s", sortType, sortOrder));
        return this;
    }

    public SolrSpatialQuery fieldList(String fieldList) {
        this.fieldList = fieldList;
        query.setFields(fieldList);
        return this;
    }

    public QueryResponse execute(SolrClient client) {
        QueryResponse response = null;
        try {
            response = client.query(collectionName, query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public <T> List<T> executeAndGetAllAs(SolrClient client, Class<T> convertToClass) {
        QueryResponse response = execute(client);
        return response.getBeans(convertToClass);
    }

    public SolrQuery toQuery() {
        return query;
    }

    @Override
    public String toString() {
        return query.toQueryString();
    }

    public enum FilterType {
        GEOFILT, BBOX, NO_FILTER;

        @Override
        public String toString() {
            return super.name().toLowerCase();
        }
    }

    public enum SortType {
        GEODIST("geodist()"); //DIST, HSIN, SQEDIST;

        private final String value;

        SortType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum SortOrder {
        ASC, DESC;

        @Override
        public String toString() {
            return super.name().toLowerCase();
        }
    }
}
