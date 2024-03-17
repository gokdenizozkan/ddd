package com.gokdenizozkan.ddd.mainservice.feature.review;

import com.gokdenizozkan.ddd.mainservice.feature.review.dto.request.ReviewSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.response.ReviewResponseMirror;
import com.gokdenizozkan.ddd.mainservice.config.response.Structured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
@Tag(name = "Review", description = "Review API. Available Rating values are: \"EXCELLENT\", \"SATISFACTORY\", \"DECENT\", \"LACKING\", \"POOR\"")
public class ReviewController {
    private final ReviewResponser responser;

    @GetMapping("/")
    @Operation(summary = "Find all reviews", description = "Find all reviews. Will only find active (enabled and non-deleted) entities.")
    public ResponseEntity<Structured<List<ReviewResponseMirror>>> findAll() {
        return responser.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find review by id", description = "Find review by id")
    public ResponseEntity<Structured<ReviewResponseMirror>> findById(@PathVariable @Positive @NotNull Long id) {
        return responser.findById(id);
    }

    @PostMapping("/")
    @Operation(summary = "Save review", description = "Save review")
    public ResponseEntity<Structured<ReviewResponseMirror>> save(@RequestBody @Valid ReviewSaveRequest request) {
        log.info("Received save request with data: {}", request);
        return responser.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update review", description = "Update review")
    public ResponseEntity<Structured<ReviewResponseMirror>> update(@PathVariable @Positive @NotNull Long id,
                                                                   @RequestBody @Valid ReviewSaveRequest request) {
        log.info("Received update request for id {} with data: {}", id, request);
        return responser.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete review", description = "Delete review")
    public ResponseEntity<Structured<Object>> delete(@PathVariable @Positive @NotNull Long id) {
        log.info("Received delete request for id {}", id);
        return responser.delete(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch review", description = "Patch review")
    public ResponseEntity<Structured<Object>> patch(@PathVariable @Positive @NotNull Long id,
                                                    @RequestParam String experience, @RequestParam String ratingString) {
        log.info("Received patch request for id {} with data: experience={}, rating={}", id, experience, ratingString);
        return responser.patch(id, experience, ratingString);
    }
}
