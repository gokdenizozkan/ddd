package com.gokdenizozkan.ddd.feature.review;

import com.gokdenizozkan.ddd.config.response.Structured;
import com.gokdenizozkan.ddd.feature.review.dto.request.ReviewSaveRequest;
import com.gokdenizozkan.ddd.feature.review.dto.response.ReviewResponseMirror;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
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
        return responser.save(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Structured<ReviewResponseMirror>> update(@PathVariable Long id, @RequestBody ReviewSaveRequest request) {
        return responser.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Structured<Object>> delete(@PathVariable Long id) {
        return responser.delete(id);
    }
}
