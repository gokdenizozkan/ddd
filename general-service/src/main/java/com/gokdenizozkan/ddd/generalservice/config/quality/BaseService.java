package com.gokdenizozkan.ddd.generalservice.config.quality;

import java.util.List;

public interface BaseService<TYPE, ID, SAVE_REQUEST> {
    List<TYPE> findAll();
    TYPE findById(ID id);
    TYPE save(SAVE_REQUEST request);

    /**
     * Update the entity with the given id with the given request
     * @param id the id of the entity to update
     * @param request the request to update the entity with
     * @return a tuple of the old and new entity
     */
    TYPE update(ID id, SAVE_REQUEST request);
    void delete(ID id);

}
