package com.gokdenizozkan.ddd.mainservice.feature.review;

import com.gokdenizozkan.ddd.mainservice.core.dtoprojection.ActiveDetermingFields;
import com.gokdenizozkan.ddd.mainservice.core.dtoprojection.StoreReviewFields;
import com.gokdenizozkan.ddd.mainservice.feature.store.Store;
import com.gokdenizozkan.ddd.mainservice.feature.store.StoreRepository;
import com.gokdenizozkan.ddd.mainservice.feature.store.StoreType;
import com.gokdenizozkan.ddd.mainservice.feature.store.dto.response.StoreDetails;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class StoreRepositoryStrub implements StoreRepository {
    @Override
    public Optional<StoreReviewFields> findStoreReviewFields(Long aLong) {
        return switch (Math.toIntExact(aLong)) {
            case 1, 2, 3, 4, 5, 6, 7, 8, 9 -> Optional.of(new StoreReviewFields(4.5F, 100L));
            case 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 -> Optional.of(new StoreReviewFields(4.5F, 100L));
            default -> Optional.empty();
        };
    }

    @Override
    public Optional<ActiveDetermingFields> findActiveDetermingFields(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<StoreDetails> findStoreDetailsById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<String> findStoreNameById(Long id) {
        return Optional.empty();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Store> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Store> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Store> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Store getOne(Long aLong) {
        return null;
    }

    @Override
    public Store getById(Long aLong) {
        return null;
    }

    @Override
    public Store getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Store> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Store> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Optional<Store> findOne(Specification<Store> spec) {
        return Optional.empty();
    }

    @Override
    public List<Store> findAll(Specification<Store> spec) {
        return null;
    }

    @Override
    public Page<Store> findAll(Specification<Store> spec, Pageable pageable) {
        return null;
    }

    @Override
    public List<Store> findAll(Specification<Store> spec, Sort sort) {
        return null;
    }

    @Override
    public long count(Specification<Store> spec) {
        return 0;
    }

    @Override
    public boolean exists(Specification<Store> spec) {
        return false;
    }

    @Override
    public long delete(Specification<Store> spec) {
        return 0;
    }

    @Override
    public <S extends Store, R> R findBy(Specification<Store> spec, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Store> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<Store> findAll() {
        return null;
    }

    @Override
    public List<Store> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public <S extends Store> S save(S entity) {
        return null;
    }

    @Override
    public Optional<Store> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Store entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Store> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Store> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Store> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Store> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Store> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Store> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Store> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Store, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
