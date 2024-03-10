package com.gokdenizozkan.ddd.feature.review;

import com.gokdenizozkan.ddd.config.quality.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends BaseRepository<Review, Long> {

}
