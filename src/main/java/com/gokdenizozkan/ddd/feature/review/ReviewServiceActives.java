package com.gokdenizozkan.ddd.feature.review;

import com.gokdenizozkan.ddd.config.Specifications;
import com.gokdenizozkan.ddd.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.core.auditableentity.ActiveDetermingFields;
import com.gokdenizozkan.ddd.feature.review.dto.ReviewEntityMapper;
import com.gokdenizozkan.ddd.feature.review.dto.request.ReviewSaveRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ReviewServiceActives")
public class ReviewServiceActives implements ReviewService {
    private final ReviewRepository repository;
    private final Specification<Review> specification;
    private final ReviewEntityMapper entityMapper;

    public ReviewServiceActives(ReviewRepository repository, ReviewEntityMapper entityMapper) {
        this.repository = repository;
        this.specification = Specifications.isActive(Review.class);
        this.entityMapper = entityMapper;
    }


    @Override
    public List<Review> findAll() {
        return repository.findAll(specification);
    }

    @Override
    public Review findById(Long id) {
        return repository.findById(specification, id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Review.class, id));
    }

    @Override
    public Review save(ReviewSaveRequest request) {
        Review review = entityMapper.fromSaveRequest.apply(request);
        return repository.save(review);
    }

    @Override
    public Review update(Long id, ReviewSaveRequest request) {
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundWithIdException(Review.class, id);
        }

        Review review = entityMapper.fromSaveRequest.apply(request);
        review.setId(id);

        ActiveDetermingFields.of(id, repository, Review.class).copyTo(review);
        return repository.save(review);
    }

    @Override
    public void delete(Long id) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Review.class, id));

        review.setDeleted(true);
        repository.save(review);
    }
}
