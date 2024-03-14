package com.gokdenizozkan.ddd.generalservice.core.dtoprojection;

import com.gokdenizozkan.ddd.generalservice.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.generalservice.config.quality.BaseRepository;
import com.gokdenizozkan.ddd.generalservice.feature.review.Rating;
import com.gokdenizozkan.ddd.generalservice.feature.review.ReviewRepository;
import com.gokdenizozkan.ddd.generalservice.feature.store.Store;
import com.gokdenizozkan.ddd.generalservice.feature.store.StoreRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public record StoreReviewFields(
        Float storeRatingAverage,
        Long reviewCount
) {
    public static StoreReviewFields of(Long id, StoreRepository repository) {
        log.info("Finding store review fields of the store with id {}", id);
        return repository.findStoreReviewFields(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Store.class, id));
    }

    public static StoreReviewFields of(Store store) {
        return new StoreReviewFields(store.getStoreRatingAverage(), store.getReviewCount());
    }

    public static void initialize(Store store) {
        Float initialStoreRatingAverage = 0F;
        Long initialReviewCount = 0L;

        log.info("Initializing store review fields of the store with id {}. Rating average is {} and review count is {}.", store.getId(), initialStoreRatingAverage, initialReviewCount);
        store.setStoreRatingAverage(initialStoreRatingAverage);
        store.setReviewCount(initialReviewCount);
        store.setReviews(new ArrayList<>());
    }

    public Float calculateNewAverageByAddingRating(Float rating) {
        log.info("Calculating new average by adding rating {} to the store with rating average {} and review count {}.", rating, storeRatingAverage, reviewCount);
        return ((storeRatingAverage * reviewCount) + rating) / (reviewCount + 1);
    }

    public Float calculateNewAverageByRemovingRating(Float rating) {
        log.info("Calculating new average by removing rating {} from the store with rating average {} and review count {}.", rating, storeRatingAverage, reviewCount);
        return (storeRatingAverage * reviewCount - rating) / (reviewCount - 1);
    }

    public StoreReviewFields ratingAdded(Float rating) {
        return new StoreReviewFields(calculateNewAverageByAddingRating(rating), reviewCount + 1);
    }

    public StoreReviewFields ratingRemoved(Float rating) {
        return new StoreReviewFields(calculateNewAverageByRemovingRating(rating), reviewCount - 1);
    }

    public StoreReviewFields ratingReplaced(Float oldRating, Float newRating) {
        return new StoreReviewFields(
                ((storeRatingAverage * reviewCount) - oldRating + newRating) / reviewCount, reviewCount);
    }

    public <T extends Store> void copyTo(T entity) {
        log.info("Setting store review fields of the provided store to rating average {} and review count {}.", storeRatingAverage, reviewCount);
        entity.setStoreRatingAverage(storeRatingAverage);
        entity.setReviewCount(reviewCount);
    }
}
