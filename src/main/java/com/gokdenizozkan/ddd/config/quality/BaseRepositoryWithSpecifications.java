package com.gokdenizozkan.ddd.config.quality;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BaseRepositoryWithSpecifications<RETURN, ID> {
    List<RETURN> findAll(Specification<RETURN> specification);
    RETURN findById(ID id, Specification<RETURN> specification);
}
