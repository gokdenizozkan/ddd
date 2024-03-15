package com.gokdenizozkan.ddd.mainservice.feature.store;

public enum StoreType {
    FOOD_STORE("foodstore");

    private final String storeType;

    StoreType(String storeType) {
        this.storeType = storeType;
    }

    @Override
    public String toString() {
        return storeType;
    }
}
