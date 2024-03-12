package com.gokdenizozkan.ddd.generalservice.feature.address;

import com.gokdenizozkan.ddd.generalservice.config.quality.BaseRepository;
import com.gokdenizozkan.ddd.generalservice.feature.address.dto.response.AddressResponseCoordinates;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends BaseRepository<Address, Long> {

    @Query("SELECT new com.gokdenizozkan.ddd.generalservice.feature.address.dto.response.AddressResponseCoordinates(a.latitude, a.longitude) FROM Address a WHERE a.id = ?1")
    Optional<AddressResponseCoordinates> findCoordinatesById(Long id);

    default Optional<Address> findCoordinatesById(Specification<Address> specification, Long id) {
        return findOne(specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id)));
    }
}
