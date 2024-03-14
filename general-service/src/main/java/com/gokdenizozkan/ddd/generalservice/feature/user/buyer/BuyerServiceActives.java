package com.gokdenizozkan.ddd.generalservice.feature.user.buyer;

import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.request.BuyerSaveRequest;
import com.gokdenizozkan.ddd.generalservice.config.Specifications;
import com.gokdenizozkan.ddd.generalservice.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.generalservice.core.dtoprojection.ActiveDetermingFields;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.BuyerEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BuyerServiceActives")
@Slf4j
public class BuyerServiceActives implements BuyerService {
    private final BuyerRepository repository;
    private final Specification<Buyer> specification;
    private final BuyerEntityMapper entityMapper;

    public BuyerServiceActives(BuyerRepository repository, BuyerEntityMapper entityMapper) {
        this.repository = repository;
        this.specification = Specifications.isActive(Buyer.class);
        this.entityMapper = entityMapper;
    }

    public List<Buyer> findAll() {
        return repository.findAll(specification);
    }

    @Override
    public Buyer findById(Long id) {
        return repository.findById(specification, id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Buyer.class, id));
    }

    @Override
    public Buyer save(BuyerSaveRequest buyerSaveRequest) {
        Buyer buyer = entityMapper.fromSaveRequest.apply(buyerSaveRequest);
        return repository.save(buyer);
    }

    @Override
    public Buyer update(Long id, BuyerSaveRequest buyerSaveRequest) {
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundWithIdException(Buyer.class, id);
        }

        Buyer buyer = entityMapper.fromSaveRequest.apply(buyerSaveRequest);
        buyer.setId(id);

        ActiveDetermingFields.of(id, repository, Buyer.class).copyTo(buyer);
        return repository.save(buyer);
    }

    @Override
    public void delete(Long id) {
        Buyer buyer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Buyer.class, id));

        buyer.setDeleted(true);
        log.info("Soft deleting buyer with id: {}", id);
        repository.save(buyer);
    }
}
