package com.gokdenizozkan.ddd.recommendationservice.feature.recommendation;

import com.gokdenizozkan.ddd.recommendationservice.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.recommendationservice.config.response.Structured;
import com.gokdenizozkan.ddd.recommendationservice.feature.recommendation.dto.response.SpatialRecommendation;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/engine/recommendation")
@RequiredArgsConstructor
public class StoreRecommendationController {
    private final RecommendationRouter router;

    @GetMapping("/stores-nearby/{latlon}")
    public ResponseEntity<Structured<SpatialRecommendation>> recommendStoresNearby(@NotBlank @PathVariable String latlon,
                                                                                   @Nullable @RequestParam(required = false) String storeType,
                                                                                   @RequestParam(required = false, defaultValue = "DEFAULT") String archetype) {
        SpatialRecommendation spatialRecommendation = router.recommendStoresNearby(latlon, storeType, archetype);
        return ResponseTemplates.ok(spatialRecommendation);
    }

}
