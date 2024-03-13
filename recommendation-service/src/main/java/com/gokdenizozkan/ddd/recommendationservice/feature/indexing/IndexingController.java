package com.gokdenizozkan.ddd.recommendationservice.feature.indexing;

import com.gokdenizozkan.ddd.recommendationservice.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.recommendationservice.config.response.Structured;
import com.gokdenizozkan.ddd.recommendationservice.config.response.StructuredResponseEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/engine/indexing")
@RequiredArgsConstructor
public class IndexingController {
    private final IndexingRouter indexingRouter;

    @PostMapping("/{storeType}/{storeId}")
    public ResponseEntity<Structured<Object>> indexStore(@PathVariable String storeType, @PathVariable String storeId,
                                                       @RequestParam String latitude, @RequestParam String longitude,
                                                       @RequestParam String name, @RequestParam Float rating) {

        UpdateResponse response = indexingRouter.indexStore(storeType, storeId, latitude, longitude, name, rating);
        HttpStatus status = response.getStatus() == 0 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;

        return StructuredResponseEntityBuilder.builder()
                .data(response.getResponse())
                .httpStatus(status)
                .success(response.getStatus() == 0)
                .message(response.getStatus() == 0 ? "Indexing successful" : "Indexing failed")
                .build();
    }

    @PutMapping("/{storeType}/{storeId}")
    public ResponseEntity<Structured<Object>> updateStoreIndex(@PathVariable String storeType, @PathVariable String storeId,
                                                             @RequestParam String latitude, @RequestParam String longitude,
                                                             @RequestParam String name, @RequestParam Float rating) {

        indexingRouter.updateStoreIndex(storeType, storeId, latitude, longitude, name, rating);
        return ResponseTemplates.noContent();
    }

    @PutMapping("/{storeType}/{storeId}")
    public ResponseEntity<Structured<Object>> updateStoreRating(@PathVariable String storeType, @PathVariable String storeId,
                                                              @RequestParam Float rating) {

        indexingRouter.updateStoreRating(storeType, storeId, rating);
        return ResponseTemplates.noContent();
    }
}
