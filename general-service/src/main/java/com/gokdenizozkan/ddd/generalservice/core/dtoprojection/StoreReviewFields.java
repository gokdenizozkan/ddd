package com.gokdenizozkan.ddd.generalservice.core.dtoprojection;

import com.gokdenizozkan.ddd.generalservice.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.generalservice.config.quality.BaseRepository;
import com.gokdenizozkan.ddd.generalservice.feature.store.Store;

public record StoreReviewFields(
        Float storeRatingAverage,
        Long reviewCount
) {
    public static <ID, T, R extends BaseRepository<T, ID>> StoreReviewFields of(ID id, R repository, Class<T> entityClass) {
        return repository.findStoreReviewFields(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(entityClass, id));
    }

    public Float calculateNewAverageByAddingRating(Float rating) {
        return ((storeRatingAverage * reviewCount) + rating) / (reviewCount + 1);
    }

    public Float calculateNewAverageByRemovingRating(Float rating) {
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
        entity.setStoreRatingAverage(storeRatingAverage);
        entity.setReviewCount(reviewCount);
    }
}
