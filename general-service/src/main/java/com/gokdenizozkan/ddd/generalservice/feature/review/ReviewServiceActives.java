package com.gokdenizozkan.ddd.generalservice.feature.review;

import com.gokdenizozkan.ddd.generalservice.client.recommendation.RecommendationClient;
import com.gokdenizozkan.ddd.generalservice.core.dtoprojection.StoreReviewFields;
import com.gokdenizozkan.ddd.generalservice.feature.review.dto.ReviewEntityMapper;
import com.gokdenizozkan.ddd.generalservice.feature.review.dto.request.ReviewSaveRequest;
import com.gokdenizozkan.ddd.generalservice.config.Specifications;
import com.gokdenizozkan.ddd.generalservice.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.generalservice.core.dtoprojection.ActiveDetermingFields;
import com.gokdenizozkan.ddd.generalservice.feature.store.Store;
import com.gokdenizozkan.ddd.generalservice.feature.store.StoreRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ReviewServiceActives")
public class ReviewServiceActives implements ReviewService {
    private final ReviewRepository repository;
    private final Specification<Review> specification;
    private final ReviewEntityMapper entityMapper;
    private final RecommendationClient recommendationClient;

    public ReviewServiceActives(ReviewRepository repository, ReviewEntityMapper entityMapper, RecommendationClient recommendationClient) {
        this.repository = repository;
        this.specification = Specifications.isActive(Review.class);
        this.entityMapper = entityMapper;
        this.recommendationClient = recommendationClient;
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
        StoreReviewFields.of(review.getStore())
                .ratingAdded(review.getRating().getValue())
                .copyTo(review.getStore());

        // Update store rating in recommendation service
        recommendationClient.updateStoreRating(
                review.getStore().getStoreType().toString(),
                review.getStore().getId().toString(),
                review.getStore().getStoreRatingAverage());

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
        StoreReviewFields.of(review.getStore())
                .copyTo(review.getStore());

        // Update store rating
        Review savedReview = repository.save(review);

        // Update store rating in recommendation service
        recommendationClient.updateStoreRating(
                savedReview.getStore().getStoreType().toString(),
                savedReview.getStore().getId().toString(),
                savedReview.getStore().getStoreRatingAverage());

        return savedReview;
    }

    @Override
    public void delete(Long id) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Review.class, id));

        review.setDeleted(true);

        StoreReviewFields.of(review.getStore())
                .ratingRemoved(review.getRating().getValue())
                .copyTo(review.getStore());

        repository.save(review);
    }

    @Override
    public void patch(Long id, String experience, String ratingString) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Review.class, id));

        if (!ratingString.isBlank()) {
            Rating rating = Rating.valueOf(ratingString);
            Float oldRating = review.getRating().getValue();
            Float newRating = rating.getValue();

            review.setRating(rating);
            StoreReviewFields.of(review.getStore())
                    .ratingReplaced(oldRating, newRating)
                    .copyTo(review.getStore());

            // Update store rating in recommendation service
            recommendationClient.updateStoreRating(
                    review.getStore().getStoreType().toString(),
                    review.getStore().getId().toString(),
                    review.getStore().getStoreRatingAverage());
        }

        if (!experience.isBlank()) {
            review.setExperience(experience);
        }

        repository.save(review);
    }
}
