package com.gokdenizozkan.ddd.mainservice.feature.address;

import com.gokdenizozkan.ddd.mainservice.config.Specifications;
import com.gokdenizozkan.ddd.mainservice.config.exception.ResourceNotActiveException;
import com.gokdenizozkan.ddd.mainservice.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.mainservice.core.dtoprojection.ActiveDetermingFields;
import com.gokdenizozkan.ddd.mainservice.feature.address.dto.AddressEntityMapper;
import com.gokdenizozkan.ddd.mainservice.feature.address.dto.request.AddressSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.address.dto.response.AddressResponseCoordinates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("AddressServiceActives")
@Slf4j
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
     * <br>
     * As an exception to the rule, this method does not use the {@link #specification} to check if the address is active,
     * but instead, it uses a custom query to improve performance with Dto Projection.<br>
     * Because of this, the return of the method is not Address, but directly, {@link AddressResponseCoordinates}.
     *
     * @param id the id of the address
     * @return the coordinates of the address
     * @throws ResourceNotActiveException if the address is not active
     * @throws ResourceNotFoundWithIdException if the address is not found
     */
    @Override
    public AddressResponseCoordinates findCoordinatesById(Long id) {
        ActiveDetermingFields.of(id, repository, Address.class)
                .ifNotActiveThrow(() -> new ResourceNotActiveException(Address.class, id));

        return repository.findCoordinatesById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Address.class, id));
    }

    @Override
    public Address save(AddressSaveRequest request) {
        Address address = entityMapper.fromSaveRequest.apply(request);
        return repository.save(address);
    }

    @Override
    /**
     * ! DISCLAIMER ! It is not advised to update addresses directly.<br>
     * If you wish to update an address, refer to the related entity and update the address from there.<br>
     * <br>
     * The reason for this is that addresses are owner-agnostic, and updating them directly can cause inconsistencies.
     * This is not an issue on its own but external systems might not be aware of this and might cause issues.
     */
    public Address update(Long id, AddressSaveRequest request) {
        log.warn("An address update is in progress. This is not advised as address entities are owner-agnostic. Please refer to the related entity and update the address from there.");
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundWithIdException(Address.class, id);
        }

        Address address = entityMapper.fromSaveRequest.apply(request);
        address.setId(id);

        ActiveDetermingFields.of(id, repository, Address.class).copyTo(address);
        repository.save(address);

        return address;
    }

    @Override
    public void delete(Long id) {
        Address address = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Address.class, id));

        log.info("Soft deleting address with id {}", id);
        address.setDeleted(true);
        repository.save(address);
    }

    @Override
    public String updateCoordinatesById(Long id, BigDecimal latitude, BigDecimal longitude) {
        log.warn("An address update is in progress. This is not advised as address entities are owner-agnostic. Please refer to the related entity and update the address from there.");
        Address address = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(Address.class, id));

        if(latitude.compareTo(address.getLatitude()) == 0 && longitude.compareTo(address.getLongitude()) == 0) {
            log.info("Ending execution of the method because coordinates were the same");
            return "Coordinates are already the same";
        }

        address.setLatitude(latitude);
        address.setLongitude(longitude);
        repository.save(address);

        return latitude + "<- lat,lon ->" + longitude;
    }

    @Override
    public String updateCoordinatesById(Long id, String latitude, String longitude) {
        BigDecimal latitudeBigDecimal = new BigDecimal(latitude);
        BigDecimal longitudeBigDecimal = new BigDecimal(longitude);

        return updateCoordinatesById(id, latitudeBigDecimal, longitudeBigDecimal);
    }
}
