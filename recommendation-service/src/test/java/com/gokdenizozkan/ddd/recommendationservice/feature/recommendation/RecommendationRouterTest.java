package com.gokdenizozkan.ddd.recommendationservice.feature.recommendation;

import com.gokdenizozkan.ddd.recommendationservice.core.solrquery.SpatialQueryArchetype;
import com.gokdenizozkan.ddd.recommendationservice.core.util.ResponseUtil;
import com.gokdenizozkan.ddd.recommendationservice.entity.store.foodstore.dto.response.FoodStoreResponse;
import com.gokdenizozkan.ddd.recommendationservice.feature.radar.RadarEngine;
import com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.dto.response.SpatialElement;
import com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.dto.response.SpatialRecommendation;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class RecommendationRouterTest {

    @Mock
    private RecommendationEngine engine;

    @Mock
    private RadarEngine radarEngine;

    @Mock
    private MockedStatic<ResponseUtil> responseUtil;

    @InjectMocks
    private RecommendationRouter underTest;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    // recommendStoresNearby
    @Test
    void whenRequestToRecommendStoresNearby_thenReturnSpatialRecommendation() {
        // Arrange
        String latlon = "30,29.9";
        String collectionName = "dddfoodstores";
        SpatialQueryArchetype<FoodStoreResponse> archetype = SpatialQueryArchetype.FoodStore.DEFAULT.get();
        SpatialRecommendation spatialRecommendation = new SpatialRecommendation(1, 4D, 4D, List.of(new SpatialElement(1F, 1L, "name", 5F, 4F, 30F, 30F)));
        QueryResponse queryResponse = mock(QueryResponse.class);
        FoodStoreResponse foodStoreResponse = new FoodStoreResponse();
        foodStoreResponse.setId("1");
        foodStoreResponse.setName("name");
        foodStoreResponse.setLatlon(List.of("30,30"));
        foodStoreResponse.setRating(5F);
        foodStoreResponse.setDistance(4F);

        when(radarEngine.findWithinRadiusWithStats(collectionName, latlon, archetype)).thenReturn(queryResponse);
        responseUtil.when(() -> ResponseUtil.findMinMaxFromStats(null, "geodist()")).thenReturn(null);
        when(queryResponse.getBeans(FoodStoreResponse.class)).thenReturn(List.of(foodStoreResponse, foodStoreResponse));

        when(engine.normalize(anyFloat(), anyFloat(), anyFloat())).thenReturn(1F);
        when(engine.normalize(anyFloat(), anyFloat())).thenReturn(1F);
        when(engine.correlate(anyFloat(), anyFloat(), anyFloat(), anyFloat())).thenReturn(1F);

        // Act
        SpatialRecommendation result = underTest.recommendStoresNearby(latlon, "dddfoodstore", "DEFAULT");

        // Assert
        assertNotNull(result);
        assertEquals(spatialRecommendation, result);
    }

}