package com.gokdenizozkan.ddd.recommendationservice.core.quality;

public interface Spatial<ST> {
    ST getDistance();
    ST getLatitude();
    ST getLongitude();
}
