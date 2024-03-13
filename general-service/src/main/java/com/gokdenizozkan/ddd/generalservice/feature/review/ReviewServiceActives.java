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
    private final StoreRepository storeRepository;

    public ReviewServiceActives(ReviewRepository repository, ReviewEntityMapper entityMapper, RecommendationClient recommendationClient,
                                StoreRepository storeRepository) {
        this.repository = repository;
        this.specification = Specifications.isActive(Review.class);
        this.entityMapper = entityMapper;
        this.recommendationClient = recommendationClient;

        this.storeRepository = storeRepository;
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
        StoreReviewFields.of(review.getStore().getId(), storeRepository, Store.class)
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
        repository.save(review);
    }
}
