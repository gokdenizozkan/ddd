package com.gokdenizozkan.ddd.recommendationservice.feature.recommendation;

import com.gokdenizozkan.ddd.recommendationservice.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.recommendationservice.config.response.Structured;
import com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.dto.response.SpatialRecommendation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/engine/recommendation")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Recommendation", description = "Recommendation API")
public class StoreRecommendationController {
    private final RecommendationRouter router;

    @GetMapping("/stores-nearby/{latlon}")
    @Operation(summary = "Recommend stores nearby", description = "Recommend stores nearby")
    public ResponseEntity<Structured<SpatialRecommendation>> recommendStoresNearby(@PathVariable @NotBlank String latlon,
                                                                                   @RequestParam(required = false) String storeType,
                                                                                   @RequestParam(required = false, defaultValue = "DEFAULT") String archetype) {
        log.info("Received request to recommend stores nearby for latlon: {}, storeType: {}, archetype: {}", latlon, storeType, archetype);
        SpatialRecommendation spatialRecommendation = router.recommendStoresNearby(latlon, storeType, archetype);
        return ResponseTemplates.ok(spatialRecommendation);
    }

}
