package com.gokdenizozkan.ddd.generalservice.feature.review;

import com.gokdenizozkan.ddd.generalservice.feature.review.dto.ReviewDtoMapper;
import com.gokdenizozkan.ddd.generalservice.feature.review.dto.request.ReviewSaveRequest;
import com.gokdenizozkan.ddd.generalservice.feature.review.dto.response.ReviewResponseMirror;
import com.gokdenizozkan.ddd.generalservice.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.generalservice.config.response.Structured;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
}
