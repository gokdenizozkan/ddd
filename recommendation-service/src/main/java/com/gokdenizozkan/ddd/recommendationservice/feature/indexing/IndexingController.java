package com.gokdenizozkan.ddd.recommendationservice.feature.indexing;

import com.gokdenizozkan.ddd.recommendationservice.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.recommendationservice.config.response.Structured;
import com.gokdenizozkan.ddd.recommendationservice.config.response.StructuredResponseEntityBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/engine/indexing")
@RequiredArgsConstructor
@Slf4j
@Validated
/**
 * ! Disclaimer ! PatchMapping is not supported by FeignClient, that is the reason why "put" mapping is used instead of "patch" for certain methods.<br>
 * This issue can be overcome by using a 3rd party library/extension called okHttp. But it is not used in this project for the sake of not adding one additional dependency.
 */
public class IndexingController {
    private final IndexingRouter indexingRouter;

    @PostMapping("/{storeType}/{storeId}")
    public ResponseEntity<Structured<Object>> indexStore(@PathVariable @NotBlank String storeType, @PathVariable @NotBlank String storeId,
                                                         @RequestParam @NotBlank String latitude, @NotBlank @RequestParam String longitude,
                                                         @RequestParam @NotBlank String name, @RequestParam @Positive @NotNull Float rating) {
        log.info("Received indexing request for storeType: {}, storeId: {}, latitude: {}, longitude: {}, name: {}, rating: {}", storeType, storeId, latitude, longitude, name, rating);
        UpdateResponse response = indexingRouter.indexStore(storeType, storeId, latitude, longitude, name, rating);
        HttpStatus status = response.getStatus() == 0 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
        return StructuredResponseEntityBuilder.builder()
                .data(response.getResponse().asShallowMap())
                .httpStatus(status)
                .success(response.getStatus() == 0)
                .message(response.getStatus() == 0 ? "Indexing successful" : "Indexing failed")
                .build();
    }

    @PutMapping("/{storeType}/{storeId}")
    public ResponseEntity<Structured<Object>> updateStoreIndex(@PathVariable @NotBlank String storeType, @PathVariable @NotBlank String storeId,
                                                               @RequestParam @NotBlank String latitude, @RequestParam @NotBlank String longitude,
                                                               @RequestParam @NotBlank String name, @RequestParam @Positive @NotNull Float rating) {
        log.info("Received index update request for storeType: {}, storeId: {}, latitude: {}, longitude: {}, name: {}, rating: {}", storeType, storeId, latitude, longitude, name, rating);
        indexingRouter.updateStoreIndex(storeType, storeId, latitude, longitude, name, rating);
        return ResponseTemplates.noContent();
    }

    @PutMapping("/{storeType}/{storeId}/rating/{rating}")
    public ResponseEntity<Structured<Object>> updateStoreRating(@PathVariable @NotBlank String storeType, @PathVariable @NotBlank String storeId,
                                                                @PathVariable @Positive @NotNull Float rating) {
        log.info("Received rating update request for storeType: {}, storeId: {}, rating: {}", storeType, storeId, rating);
        indexingRouter.updateStoreRating(storeType, storeId, rating);
        return ResponseTemplates.noContent();
    }

    @PutMapping("/{storeType}/{storeId}/name/{name}")
    public ResponseEntity<Structured<Object>> updateStoreName(@PathVariable @NotBlank String storeType, @PathVariable @NotBlank String storeId,
                                                              @PathVariable @NotBlank String name) {
        log.info("Received name update request for storeType: {}, storeId: {}, name: {}", storeType, storeId, name);
        indexingRouter.updateStoreName(storeType, storeId, name);
        return ResponseTemplates.noContent();
    }

    @PutMapping("/{storeType}/{storeId}/coordinates")
    public ResponseEntity<Structured<Object>> updateStoreCoordinates(@PathVariable @NotBlank String storeType, @PathVariable @NotBlank String storeId,
                                                                     @RequestParam @NotBlank String latitude, @RequestParam @NotBlank String longitude) {
        log.info("Received coordinates update request for storeType: {}, storeId: {}, latitude: {}, longitude: {}", storeType, storeId, latitude, longitude);
        indexingRouter.updateStoreLatlon(storeType, storeId, latitude, longitude);
        return ResponseTemplates.noContent();
    }

    @DeleteMapping("/{storeType}/{storeId}")
    public ResponseEntity<Structured<Object>> deleteStoreIndex(@PathVariable @NotBlank String storeType, @PathVariable @NotBlank String storeId) {
        log.info("Received delete request for storeType: {}, storeId: {}", storeType, storeId);
        indexingRouter.deleteStoreIndex(storeType, storeId);
        return ResponseTemplates.noContent();
    }
}
