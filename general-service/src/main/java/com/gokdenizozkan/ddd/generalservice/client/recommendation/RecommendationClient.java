package com.gokdenizozkan.ddd.generalservice.client.recommendation;

import com.gokdenizozkan.ddd.generalservice.config.response.Structured;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@FeignClient(name = "recommendation-service", url = "${ddd.client.recommendation-service.complete-url}")
/**
 * ! Disclaimer ! PatchMapping is not supported by FeignClient, that is the reason why "put" mapping is used instead of "patch" for certain methods.<br>
 * This issue can be overcome by using a 3rd party library/extension called okHttp. But it is not used in this project for the sake of not adding one additional dependency.
 */
public interface RecommendationClient {

    @PostMapping("/engine/indexing/{storeType}/{storeId}")
    ResponseEntity<Structured<Object>> indexStore(@PathVariable String storeType, @PathVariable String storeId,
                                                  @RequestParam String latitude, @RequestParam String longitude,
                                                  @RequestParam String name, @RequestParam Float rating);

    @PutMapping("/engine/indexing/{storeType}/{storeId}")
    ResponseEntity<Structured<Object>> updateStoreIndex(@PathVariable String storeType, @PathVariable String storeId,
                                                        @RequestParam String latitude, @RequestParam String longitude,
                                                        @RequestParam String name, @RequestParam Float rating);

    @PutMapping("/engine/indexing/{storeType}/{storeId}/rating/{rating}")
    ResponseEntity<Structured<Object>> updateStoreRating(@PathVariable String storeType, @PathVariable String storeId,
                                                         @PathVariable Float rating);

    @PutMapping("/engine/indexing/{storeType}/{storeId}/name/{name}")
    ResponseEntity<Structured<Object>> updateStoreName(@PathVariable String storeType, @PathVariable String storeId,
                                                       @PathVariable String name);
    
    @PutMapping("/engine/indexing/{storeType}/{storeId}/coordinates")
    ResponseEntity<Structured<Object>> updateStoreCoordinates(@PathVariable String storeType, @PathVariable String storeId,
                                                             @RequestParam String latitude, @RequestParam String longitude);
}
