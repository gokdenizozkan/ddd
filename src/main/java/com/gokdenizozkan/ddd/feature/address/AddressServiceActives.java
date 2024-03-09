package com.gokdenizozkan.ddd.feature.address;

import com.gokdenizozkan.ddd.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.core.datastructure.Tuple;
import com.gokdenizozkan.ddd.feature.address.dto.AddressEntityMapper;
import com.gokdenizozkan.ddd.feature.address.dto.request.AddressSaveRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceActives implements AddressService {
    private final AddressRepository repository;
    private final Specification<Address> specification;
    private final AddressEntityMapper entityMapper;


    public AddressServiceActives(AddressRepository repository,
                                 @Qualifier("SpecificationActives") Specification<Address> specification,
                                 AddressEntityMapper entityMapper) {
        this.repository = repository;
        this.specification = specification;
        this.entityMapper = entityMapper;
    }

    @Override
    public List<Address> findAll() {
        return repository.findAll(specification);
    }

    @Override
    public Address findById(Long id) {
        return repository.findById(id, specification);
    }

    @Override
    public Address save(AddressSaveRequest request) {
        Address address = entityMapper.fromSaveRequest.apply(request);
        return repository.save(address);
    }

    @Override
    public Tuple<Address> update(Long id, AddressSaveRequest request) {
        Address foundAddress = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Address.class, id));

        Address updatedAddress = entityMapper.fromSaveRequest.apply(request);
        repository.save(updatedAddress);

        return Tuple.of(foundAddress, updatedAddress);
    }

    @Override
    public void delete(Long id) {
        Address address = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Address.class, id));

        address.setDeleted(true);
        repository.save(address);
    }
}
