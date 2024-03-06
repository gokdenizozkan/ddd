package com.gokdenizozkan.ddd.user.buyer;

import com.gokdenizozkan.ddd.addresscollection.AddressCollection;
import com.gokdenizozkan.ddd.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private AddressCollection addressCollection;
}
