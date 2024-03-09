package com.gokdenizozkan.ddd.config.quality;

import com.gokdenizozkan.ddd.core.auditableentity.ActiveDetermingFields;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;


@NoRepositoryBean
public interface BaseRepository <T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    @Query("SELECT new com.gokdenizozkan.ddd.core.auditableentity.ActiveDetermingFields(a.deleted, a.enabled) FROM #{#entityName} a WHERE a.id = ?1")
    ActiveDetermingFields findActiveDetermingFields(ID id);

    default Optional<T> findById(Specification<T> specification, ID id) {
        return findOne(specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id)));
    }
}
