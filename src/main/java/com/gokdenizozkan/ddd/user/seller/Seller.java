package com.gokdenizozkan.ddd.user.seller;

import com.gokdenizozkan.ddd.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
