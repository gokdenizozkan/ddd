package com.gokdenizozkan.ddd.mainservice.feature.review;

import com.gokdenizozkan.ddd.mainservice.config.quality.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends BaseRepository<Review, Long> {

    @Query("SELECT r.rating FROM Review r WHERE r.id = :id")
    Optional<Rating> findRatingById(Long id);
}
