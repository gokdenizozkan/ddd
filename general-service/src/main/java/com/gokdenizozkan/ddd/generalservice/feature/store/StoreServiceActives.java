package com.gokdenizozkan.ddd.generalservice.feature.store;

import com.gokdenizozkan.ddd.generalservice.core.dtoprojection.StoreReviewFields;
import com.gokdenizozkan.ddd.generalservice.feature.store.dto.StoreEntityMapper;
import com.gokdenizozkan.ddd.generalservice.feature.store.dto.request.StoreSaveRequest;
import com.gokdenizozkan.ddd.generalservice.feature.store.dto.response.StoreDetails;
import com.gokdenizozkan.ddd.generalservice.config.Specifications;
import com.gokdenizozkan.ddd.generalservice.config.exception.ResourceNotActiveException;
import com.gokdenizozkan.ddd.generalservice.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.generalservice.core.dtoprojection.ActiveDetermingFields;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StoreServiceActives")
public class StoreServiceActives implements StoreService {
    private final StoreRepository repository;
    private final Specification<Store> specification;
    private final StoreEntityMapper entityMapper;

    public StoreServiceActives(StoreRepository repository,
                               StoreEntityMapper entityMapper) {
        this.repository = repository;
        this.specification = Specifications.isActive(Store.class);
        this.entityMapper = entityMapper;
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
    public Store save(StoreSaveRequest request) {
        Store store = entityMapper.fromSaveRequest.apply(request);
        StoreReviewFields.initialize(store);

        return repository.save(store);
    }

    @Override
    public Store update(Long id, StoreSaveRequest request) {
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundWithIdException(Store.class, id);
        }

        Store store = entityMapper.fromSaveRequest.apply(request);
        store.setId(id);

        ActiveDetermingFields.of(id, repository, Store.class).copyTo(store);
        repository.save(store);

        return store;
    }

    @Override
    public void delete(Long id) {
        Store store = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Store.class, id));

        store.setDeleted(true);
        repository.save(store);
    }
}