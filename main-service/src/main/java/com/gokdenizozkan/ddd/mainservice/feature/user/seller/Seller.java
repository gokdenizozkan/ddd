package com.gokdenizozkan.ddd.mainservice.feature.user.seller;

import com.gokdenizozkan.ddd.mainservice.feature.store.Store;
import com.gokdenizozkan.ddd.mainservice.feature.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@RequiredArgsConstructor
public class Seller extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sellers_gen")
    @SequenceGenerator(name = "sellers_gen", sequenceName = "sellers_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SellerAuthority authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store store;
}
