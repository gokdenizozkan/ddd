package com.gokdenizozkan.ddd.recommendationservice.feature.recommendation;

import com.gokdenizozkan.ddd.recommendationservice.config.response.Structured;
import com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.dto.response.SpatialElement;
import com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.dto.response.SpatialRecommendation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StoreRecommendationControllerTest {

    @Mock
    private RecommendationRouter router;

    @InjectMocks
    private StoreRecommendationController underTest;

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
        String storeType = "foodstore";
        String archetype = "DEFAULT";
        SpatialElement spatialElement = new SpatialElement(1F, 1L, "name", 5F, 4F, 30F, 30F);
        SpatialRecommendation spatialRecommendation = new SpatialRecommendation(1, 4D, 4D, List.of(spatialElement));

        when(router.recommendStoresNearby(latlon, storeType, archetype)).thenReturn(spatialRecommendation);

        // Act
        ResponseEntity<Structured<SpatialRecommendation>> response = underTest.recommendStoresNearby(latlon, storeType, archetype);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(spatialRecommendation, response.getBody().data());
        verify(router, times(1)).recommendStoresNearby(latlon, storeType, archetype);
    }

}