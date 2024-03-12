package com.gokdenizozkan.ddd.recommendationservice.core.util;

import com.gokdenizozkan.ddd.recommendationservice.core.datastructure.Tuple;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.QueryResponse;

public class ResponseUtil {
    public static Tuple<Double> findMinMaxFromStats(QueryResponse response, String fieldName) {
        FieldStatsInfo fieldStatsInfo = response.getFieldStatsInfo().get(fieldName);
        return Tuple.of((Double) fieldStatsInfo.getMin(), (Double) fieldStatsInfo.getMax());
    }
}
