package com.gokdenizozkan.ddd.feature.user.buyer;

import com.gokdenizozkan.ddd.feature.address.Address;
import com.gokdenizozkan.ddd.feature.review.Review;
import com.gokdenizozkan.ddd.feature.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "buyers")
@Getter
@Setter
@RequiredArgsConstructor
public class Buyer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buyer_gen")
    @SequenceGenerator(name = "buyer_gen", sequenceName = "buyer_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
            name = "buyer_addresses",
            joinColumns = @JoinColumn(name = "buyer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id", unique = true)
    )
    private List<Address> addresses;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
