package com.gokdenizozkan.ddd.generalservice.feature.user.buyer;

import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.response.BuyerDetails;
import com.gokdenizozkan.ddd.generalservice.config.quality.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyerRepository extends BaseRepository<Buyer, Long> {

    @Query("SELECT new com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.response.BuyerDetails(b.id, b.name, b.surname, b.email, b.phone, b.birthdate) FROM Buyer b")
    List<BuyerDetails> findAllAsBuyerDetails();
}
