package com.gokdenizozkan.ddd.mainservice.feature.review;

import com.gokdenizozkan.ddd.mainservice.config.exception.InvalidInputException;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.ReviewDtoMapper;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.request.ReviewSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.response.ReviewResponseMirror;
import com.gokdenizozkan.ddd.mainservice.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.mainservice.config.response.Structured;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ReviewResponser {
    private final ReviewService service;
    private final ReviewDtoMapper dtoMapper;

    public ReviewResponser(@Qualifier("ReviewServiceActives") ReviewService service,
                           ReviewDtoMapper dtoMapper) {
        this.service = service;
        this.dtoMapper = dtoMapper;
    }

    public ResponseEntity<Structured<List<ReviewResponseMirror>>> findAll() {
        List<Review> reviews = service.findAll();
        List<ReviewResponseMirror> response = reviews.stream().map(dtoMapper.toResponseMirror).toList();
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<ReviewResponseMirror>> findById(Long id) {
        Review review = service.findById(id);
        ReviewResponseMirror response = dtoMapper.toResponseMirror.apply(review);
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<ReviewResponseMirror>> save(ReviewSaveRequest request) {
        Review review = service.save(request);
        ReviewResponseMirror response = dtoMapper.toResponseMirror.apply(review);
        return ResponseTemplates.created(response);
    }

    public ResponseEntity<Structured<ReviewResponseMirror>> update(Long id, ReviewSaveRequest request) {
        Review review = service.update(id, request);
        ReviewResponseMirror response = dtoMapper.toResponseMirror.apply(review);
        return ResponseTemplates.ok(response);
    }

    public ResponseEntity<Structured<Object>> delete(Long id) {
        service.delete(id);
        return ResponseTemplates.noContent();
    }

    public ResponseEntity<Structured<Object>> patch(Long id, String experience, String ratingString) {
        if (ratingString.isBlank() && experience.isBlank()) {
            log.warn("Both experience and rating are empty. Cannot proceed to patch.");
            throw new InvalidInputException("Both experience and rating cannot be empty.");
        }

        service.patch(id, experience, ratingString);
        return ResponseTemplates.noContent();
    }
}
