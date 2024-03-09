package com.gokdenizozkan.ddd.feature.review;

import com.gokdenizozkan.ddd.core.auditableentity.AuditableEntity;
import com.gokdenizozkan.ddd.feature.store.Store;
import com.gokdenizozkan.ddd.feature.user.buyer.Buyer;
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
@Table(name = "reviews")
@Getter
@Setter
@RequiredArgsConstructor
public class Review extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_gen")
    @SequenceGenerator(name = "reviews_gen", sequenceName = "reviews_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Rating rating;
    private String experience;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    private Buyer buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store store;
}
