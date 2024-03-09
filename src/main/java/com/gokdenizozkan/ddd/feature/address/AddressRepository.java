package com.gokdenizozkan.ddd.feature.address;

import com.gokdenizozkan.ddd.config.quality.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends BaseRepository<Address, Long> {

}
