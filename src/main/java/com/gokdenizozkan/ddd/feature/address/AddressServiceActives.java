package com.gokdenizozkan.ddd.feature.address;

import com.gokdenizozkan.ddd.config.Specifications;
import com.gokdenizozkan.ddd.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.feature.address.dto.AddressEntityMapper;
import com.gokdenizozkan.ddd.feature.address.dto.request.AddressSaveRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceActives implements AddressService {
    private final AddressRepository repository;
    private final Specification<Address> specification;
    private final AddressEntityMapper entityMapper;


    public AddressServiceActives(AddressRepository repository,
                                 AddressEntityMapper entityMapper) {
        this.repository = repository;
        this.specification = Specifications.isActive(Address.class);
        this.entityMapper = entityMapper;
    }

    @Override
    public List<Address> findAll() {
        return repository.findAll(specification);
    }

    @Override
    public Address findById(Long id) {
        return repository.findById(specification, id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Address.class, id));
    }

    @Override
    public Address save(AddressSaveRequest request) {
        Address address = entityMapper.fromSaveRequest.apply(request);
        return repository.save(address);
    }

    @Override
    public Address update(Long id, AddressSaveRequest request) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundWithIdException(Address.class, id);
        }

        Address address = entityMapper.fromSaveRequest.apply(request);
        address.setId(id);

        repository.findActiveDetermingFields(id).copyTo(address);
        repository.save(address);

        return address;
    }

    @Override
    public void delete(Long id) {
        Address address = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Address.class, id));

        address.setDeleted(true);
        repository.save(address);
    }
}
