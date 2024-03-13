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

    public Float findAverageWithNewRating(Float rating) {
        return ((storeRatingAverage * reviewCount) + rating) / (reviewCount + 1);
    }

    public StoreReviewFields ratingAdded(Float rating) {
        return new StoreReviewFields(findAverageWithNewRating(rating), reviewCount + 1);
    }

    public <T extends Store> void copyTo(T entity) {
        entity.setStoreRatingAverage(storeRatingAverage);
        entity.setReviewCount(reviewCount);
    }
}
