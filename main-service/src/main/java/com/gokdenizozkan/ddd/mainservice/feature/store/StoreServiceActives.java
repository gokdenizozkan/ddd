package com.gokdenizozkan.ddd.mainservice.feature.store;

import com.gokdenizozkan.ddd.mainservice.client.recommendation.RecommendationClient;
import com.gokdenizozkan.ddd.mainservice.core.dtoprojection.StoreReviewFields;
import com.gokdenizozkan.ddd.mainservice.feature.store.dto.StoreEntityMapper;
import com.gokdenizozkan.ddd.mainservice.feature.store.dto.request.StoreSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.store.dto.response.StoreDetails;
import com.gokdenizozkan.ddd.mainservice.config.Specifications;
import com.gokdenizozkan.ddd.mainservice.config.exception.ResourceNotActiveException;
import com.gokdenizozkan.ddd.mainservice.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.mainservice.core.dtoprojection.ActiveDetermingFields;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("StoreServiceActives")
@Slf4j
public class StoreServiceActives implements StoreService {
    private final StoreRepository repository;
    private final Specification<Store> specification;
    private final StoreEntityMapper entityMapper;
    private final RecommendationClient recommendationClient;

    public StoreServiceActives(StoreRepository repository,
                               StoreEntityMapper entityMapper,
                               RecommendationClient recommendationClient) {
        this.repository = repository;
        this.specification = Specifications.isActive(Store.class);
        this.entityMapper = entityMapper;
        this.recommendationClient = recommendationClient;
    }

    @Override
    public List<Store> findAll() {
        return repository.findAll(specification);
    }

    @Override
    public Store findById(Long id) {
        return repository.findById(specification, id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Store.class, id));
    }

    @Override
    public StoreDetails findStoreDetailsById(Long id) {
        ActiveDetermingFields.of(id, repository, Store.class)
                .ifNotActiveThrow(() -> new ResourceNotActiveException(Store.class, id));

        return repository.findStoreDetailsById(id).get();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Store save(StoreSaveRequest request) {
        Store store = entityMapper.fromSaveRequest.apply(request);
        StoreReviewFields.initialize(store);

        Store savedStore = repository.save(store);

        log.info("Indexing store in recommendation service: {}", savedStore);
        recommendationClient.indexStore(
                savedStore.getStoreType().toString(),
                savedStore.getId().toString(),
                savedStore.getAddress().getLatitude().toString(),
                savedStore.getAddress().getLongitude().toString(),
                savedStore.getName(),
                savedStore.getStoreRatingAverage());

        return savedStore;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Store update(Long id, StoreSaveRequest request) {
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundWithIdException(Store.class, id);
        }

        Store store = entityMapper.fromSaveRequest.apply(request);
        store.setId(id);

        ActiveDetermingFields.of(id, repository, Store.class).copyTo(store);
        StoreReviewFields.of(id, repository).copyTo(store);

        Store updatedStore = repository.save(store);

        if (!request.name().equals(repository.findStoreNameById(id).get())) {
            log.info("Updating store name in recommendation service: {}", store);
            recommendationClient.updateStoreName(
                    store.getStoreType().toString(),
                    store.getId().toString(),
                    request.name());
        }

        return updatedStore;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(Long id) {
        Store store = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Store.class, id));

        store.setDeleted(true);
        log.info("Soft deleting store with id {}.", store);
        repository.save(store);
        log.info("Deleting store index in recommendation service: {}", store);
        recommendationClient.deleteStoreIndex(
                store.getStoreType().toString(),
                store.getId().toString());
    }

    @Override
    public String updateStoreNameById(Long id, String name) {
        Store store = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Store.class, id));

        if (name.equals(store.getName())) {
            log.info("Name update process execution is halted because name is the same for store with id: {}", id);
            return name;
        }

        store.setName(name);
        repository.save(store);
        log.info("Updating store name in recommendation service: {}", store);
        recommendationClient.updateStoreName(
                store.getStoreType().toString(),
                store.getId().toString(),
                name);

        return name;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String updateCoordinatesById(Long id, BigDecimal latitude, BigDecimal longitude) {
        Store store = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Store.class, id));

        if (latitude.compareTo(store.getAddress().getLatitude()) == 0
                && longitude.compareTo(store.getAddress().getLongitude()) == 0) {
            log.info("Coordinates update process execution is halted because coordinates are the same for store with id: {}", id);
            return "Coordinates are the same";
        }

        store.getAddress().setLatitude(latitude);
        store.getAddress().setLongitude(longitude);
        repository.save(store);
        log.info("Updating store coordinates in recommendation service: latitude {}, longitude {}", latitude, longitude);
        recommendationClient.updateStoreCoordinates(
                store.getStoreType().toString(),
                store.getId().toString(),
                latitude.toString(),
                longitude.toString());

        return latitude + "<- lat,lon ->" + longitude;
    }

    @Override
    public String updateCoordinatesById(Long id, String latitude, String longitude) {
        BigDecimal latitudeBigDecimal = new BigDecimal(latitude);
        BigDecimal longitudeBigDecimal = new BigDecimal(longitude);

        return updateCoordinatesById(id, latitudeBigDecimal, longitudeBigDecimal);
    }
}