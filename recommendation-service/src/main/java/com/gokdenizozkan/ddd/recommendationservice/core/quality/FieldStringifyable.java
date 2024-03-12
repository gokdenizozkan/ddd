package com.gokdenizozkan.ddd.recommendationservice.core.quality;

import com.gokdenizozkan.ddd.recommendationservice.core.SolrSpatialQuery;

import java.util.Arrays;

/**
 * This interface is used to stringify fields of a class.<br>
 * <br>
 * There is a static method (implementation) {@link FieldStringifyable#stringifyFields(Class)} which returns a string of field names separated by comma.<br>
 * This implementation can be used to achieve the desired behaviour of {@link SolrSpatialQuery#fieldList(Class)}.
 */
public interface FieldStringifyable {
    String stringifyFields();
    
    static String stringifyFields(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> sb.append(field.getName()).append(","));
        return sb.toString();
    }
}
