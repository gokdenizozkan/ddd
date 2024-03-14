package com.gokdenizozkan.ddd.generalservice.feature.review;

import com.gokdenizozkan.ddd.generalservice.feature.review.dto.request.ReviewSaveRequest;
import com.gokdenizozkan.ddd.generalservice.feature.review.dto.response.ReviewResponseMirror;
import com.gokdenizozkan.ddd.generalservice.config.response.Structured;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewResponser responser;

    @GetMapping("/")
    public ResponseEntity<Structured<List<ReviewResponseMirror>>> findAll() {
        return responser.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Structured<ReviewResponseMirror>> findById(@PathVariable Long id) {
        return responser.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Structured<ReviewResponseMirror>> save(@RequestBody ReviewSaveRequest request) {
        log.info("Received save request with data: {}", request);
        return responser.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Structured<ReviewResponseMirror>> update(@PathVariable Long id, @RequestBody ReviewSaveRequest request) {
        log.info("Received update request for id {} with data: {}", id, request);
        return responser.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Structured<Object>> delete(@PathVariable Long id) {
        log.info("Received delete request for id {}", id);
        return responser.delete(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Structured<Object>> patch(@PathVariable Long id,
                                                    @RequestParam String experience, @RequestParam String ratingString) {
        log.info("Received patch request for id {} with data: experience={}, rating={}", id, experience, ratingString);
        return responser.patch(id, experience, ratingString);
    }
}
