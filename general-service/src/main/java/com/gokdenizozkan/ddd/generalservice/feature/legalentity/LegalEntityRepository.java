package com.gokdenizozkan.ddd.generalservice.feature.legalentity;

import com.gokdenizozkan.ddd.generalservice.config.quality.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LegalEntityRepository  extends BaseRepository<LegalEntity, Long> {
    default Optional<LegalEntity> mockFindById(Long id) {
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setId(id);
        return Optional.of(legalEntity);
    }
}
