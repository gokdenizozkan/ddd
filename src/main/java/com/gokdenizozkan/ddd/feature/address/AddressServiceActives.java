package com.gokdenizozkan.ddd.feature.address;

import com.gokdenizozkan.ddd.config.Specifications;
import com.gokdenizozkan.ddd.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.core.auditableentity.ActiveDetermingFields;
import com.gokdenizozkan.ddd.feature.address.dto.AddressEntityMapper;
import com.gokdenizozkan.ddd.feature.address.dto.request.AddressSaveRequest;
import com.gokdenizozkan.ddd.feature.address.dto.response.AddressResponseCoordinates;
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

    /**
     * Finds the coordinates of the address with the given id.<br>
     * If the address is not active, throws a {@link com.gokdenizozkan.ddd.config.exception.NotActiveException}.<br>
     * If the address is not found, throws a {@link com.gokdenizozkan.ddd.config.exception.ResourceNotFoundWithIdException}.<br>
     * <br>
     * As an exception to the rule, this method does not use the {@link #specification} to check if the address is active,
     * but instead, it uses a custom query to improve performance with Dto Projection.<br>
     * Because of this, the return of the method is not Address, but directly, @{@link AddressResponseCoordinates}.
     *
     * @param id the id of the address
     * @return the coordinates of the address
     */
    @Override
    public AddressResponseCoordinates findCoordinatesById(Long id) {
        ActiveDetermingFields activeDetermingFields = repository.findActiveDetermingFields(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Address.class, id));

        boolean active = activeDetermingFields.isActive();
        if (!active) {
            throw new ResourceNotFoundWithIdException(Address.class, id);
        }


        return repository.findCoordinatesById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Address.class, id));
    }

    @Override
    public Address save(AddressSaveRequest request) {
        Address address = entityMapper.fromSaveRequest.apply(request);
        return repository.save(address);
    }

    @Override
    public Address update(Long id, AddressSaveRequest request) {
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundWithIdException(Address.class, id);
        }

        Address address = entityMapper.fromSaveRequest.apply(request);
        address.setId(id);

        repository.findActiveDetermingFields(id).get().copyTo(address);
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
