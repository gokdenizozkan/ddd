package com.gokdenizozkan.ddd.generalservice.feature.store;

import com.gokdenizozkan.ddd.generalservice.feature.review.Rating;
import com.gokdenizozkan.ddd.generalservice.feature.store.dto.response.StoreDetails;
import com.gokdenizozkan.ddd.generalservice.config.quality.BaseRepository;
import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Embeddable
public interface StoreRepository extends BaseRepository<Store, Long> {

    @Query("SELECT new com.gokdenizozkan.ddd.generalservice.feature.store.dto.response.StoreDetails(s.id, s.name, s.email, s.phone, s.storeType, s.storeRatingAverage, s.reviewCount) FROM Store s WHERE s.id = :id")
    Optional<StoreDetails> findStoreDetailsById(Long id);

    @Query("SELECT s.name FROM Store s WHERE s.id = :id")
    Optional<String> findStoreNameById(Long id);
}