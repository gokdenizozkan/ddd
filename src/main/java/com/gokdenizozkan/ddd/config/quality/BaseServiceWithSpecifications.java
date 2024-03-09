package com.gokdenizozkan.ddd.config.quality;

import com.gokdenizozkan.ddd.core.datastructure.Tuple;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BaseServiceWithSpecifications<TYPE, ID, SAVE_REQUEST> {
    List<TYPE> findAll();
    TYPE findById(ID id);
    TYPE save(SAVE_REQUEST request);

    /**
     * Update the entity with the given id with the given request
     * @param id the id of the entity to update
     * @param request the request to update the entity with
     * @return a tuple of the old and new entity
     */
    Tuple<TYPE> update(ID id, SAVE_REQUEST request);
    void delete(ID id);

}
