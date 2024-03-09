package com.gokdenizozkan.ddd.feature.address;

import com.gokdenizozkan.ddd.config.quality.BaseRepositoryWithSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, BaseRepositoryWithSpecifications<Address, Long> {

    @Override
    List<Address> findAll(Specification<Address> specification);

    @Override
    Address findById(Long id, Specification<Address> specification);
}
