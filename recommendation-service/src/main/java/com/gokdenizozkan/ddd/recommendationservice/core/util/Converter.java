package com.gokdenizozkan.ddd.recommendationservice.core.util;

public class Converter {
    public static String toCollectionName(String storeType) {
        return new StringBuilder("ddd").append(storeType).append("s").toString();
    }
}
