package com.gokdenizozkan.ddd.mainservice.feature.review;

import com.gokdenizozkan.ddd.mainservice.core.dtoprojection.ActiveDetermingFields;
import com.gokdenizozkan.ddd.mainservice.core.dtoprojection.StoreReviewFields;
import com.gokdenizozkan.ddd.mainservice.feature.store.Store;
import com.gokdenizozkan.ddd.mainservice.feature.store.StoreType;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Review repository is used by both the related services and certain static methods outside of the services' boundaries.
 * This resulted in the need for a stub implementation of the repository to be used in the tests. Otherwise, it was almost impossible to test the service.
 */
public class ReviewRepositoryStub implements ReviewRepository {

    @Override
    public void deleteById(Long aLong) {
    }

    @Override
    public boolean existsById(Long aLong) {
        return switch (Math.toIntExact(aLong)) {
            case 1, 2, 3, 4, 5, 6, 7, 8, 9 -> true; // active
            case 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 -> true; // not active
            case 61 -> true;
            default -> false; // non-existent
        };
    }

    int findAllCount = 0;

    @Override
    public List<Review> findAll(Specification<Review> spec) {
        if (findAllCount == 0) {
            findAllCount++;
            return List.of(new Review());
        }
        if (findAllCount == 1) {
            findAllCount++;
            return Collections.emptyList();
        }
        return null;
    }

    boolean setDeletedFor61 = false;

    @Override
    public Optional<Review> findById(Specification<Review> specification, Long id) {
        Store store = new Store();
        store.setId(1L);
        store.setStoreType(StoreType.FOOD_STORE);
        store.setReviewCount(20L);
        store.setStoreRatingAverage(4.5F);

        Review review = new Review();
        review.setId(1L);
        review.setExperience("exp");
        review.setRating(Rating.EXCELLENT);
        review.setStore(store);

        if (id == 61 && setDeletedFor61) {
            return Optional.empty();
        }

        return switch (Math.toIntExact(id)) {
            case 1, 2, 3, 4, 5, 6, 7, 8, 9 -> Optional.of(new Review());
            case 61 -> Optional.of(review);
            default -> Optional.empty();
        };
    }

    @Override
    public Optional<Review> findOne(Specification<Review> spec) {
        return switch (Math.toIntExact(spec.hashCode())) {
            case 1 -> Optional.of(new Review());
            default -> Optional.empty();
        };
    }

    @Override
    public <S extends Review> S save(S entity) {
        entity.setId(1L);
        return entity;
    }

    @Override
    public Optional<ActiveDetermingFields> findActiveDetermingFields(Long aLong) {

        return switch (Math.toIntExact(aLong)) {
            case 1, 2, 3, 4, 5, 6, 7, 8, 9 -> Optional.of(new ActiveDetermingFields(false, true));
            case 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 -> Optional.of(new ActiveDetermingFields(true, false));
            default -> Optional.empty();
        };
    }

    @Override
    public Optional<StoreReviewFields> findStoreReviewFields(Long aLong) {
        return switch (Math.toIntExact(aLong)) {
            case 1, 2, 3, 4, 5, 6, 7, 8, 9 -> Optional.of(new StoreReviewFields(4.5F, 100L));
            case 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 -> Optional.of(new StoreReviewFields(2.5F, 10L));
            default -> Optional.empty();
        };
    }

    @Override
    public Optional<Rating> findRatingById(Long id) {
        return switch (Math.toIntExact(id)) {
            case 1, 2, 3, 4, 5, 6, 7, 8, 9 -> Optional.of(Rating.EXCELLENT);
            case 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 -> Optional.of(Rating.POOR);
            default -> Optional.empty();
        };
    }


    @Override
    public Optional<Review> findById(Long aLong) {
        Store store = new Store();
        store.setId(1L);
        store.setStoreType(StoreType.FOOD_STORE);
        store.setReviewCount(20L);
        store.setStoreRatingAverage(4.5F);

        Review review = new Review();
        review.setId(1L);
        review.setExperience("exp");
        review.setRating(Rating.EXCELLENT);
        review.setStore(store);

        return switch (Math.toIntExact(aLong)) {
            case 1, 2, 3, 4, 5, 6, 7, 8, 9 -> Optional.of(review);
            case 61 -> Optional.of(review);
            default -> Optional.empty();
        };
    }


























    @Override
    public void flush() {

    }

    @Override
    public <S extends Review> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Review> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Review> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Review getOne(Long aLong) {
        return null;
    }

    @Override
    public Review getById(Long aLong) {
        return null;
    }

    @Override
    public Review getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Review> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Review> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Review> findAll(Specification<Review> spec, Pageable pageable) {
        return null;
    }

    @Override
    public List<Review> findAll(Specification<Review> spec, Sort sort) {
        return null;
    }

    @Override
    public long count(Specification<Review> spec) {
        return 0;
    }

    @Override
    public boolean exists(Specification<Review> spec) {
        return false;
    }

    @Override
    public long delete(Specification<Review> spec) {
        return 0;
    }

    @Override
    public <S extends Review, R> R findBy(Specification<Review> spec, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Review> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<Review> findAll() {
        return null;
    }

    @Override
    public List<Review> findAllById(Iterable<Long> longs) {
        return null;
    }


    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Review entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Review> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Review> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Review> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Review> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Review> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Review> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Review> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Review, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
