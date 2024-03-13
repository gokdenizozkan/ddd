package com.gokdenizozkan.ddd.generalservice.client.recommendation;

import com.gokdenizozkan.ddd.generalservice.config.response.Structured;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@FeignClient(name = "recommendation-service", url = "${ddd.client.recommendation-service.complete-url}")
public interface RecommendationClient {

    @PostMapping("/engine/indexing/{storeType}/{storeId}")
    ResponseEntity<Structured<Object>> indexStore(@PathVariable String storeType, @PathVariable String storeId,
                                                  @RequestParam String latitude, @RequestParam String longitude,
                                                  @RequestParam String name, @RequestParam Float rating);

    @PutMapping("/engine/indexing/{storeType}/{storeId}")
    ResponseEntity<Structured<Object>> updateStoreIndex(@PathVariable String storeType, @PathVariable String storeId,
                                                        @RequestParam String latitude, @RequestParam String longitude,
                                                        @RequestParam String name, @RequestParam Float rating);

    @PutMapping("/engine/indexing/{storeType}/{storeId}")
    ResponseEntity<Structured<Object>> updateStoreRating(@PathVariable String storeType, @PathVariable String storeId,
                                                         @RequestParam Float rating);
}
