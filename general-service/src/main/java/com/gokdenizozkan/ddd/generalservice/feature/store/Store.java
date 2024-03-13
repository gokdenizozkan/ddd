package com.gokdenizozkan.ddd.generalservice.feature.store;

import com.gokdenizozkan.ddd.generalservice.feature.review.Review;
import com.gokdenizozkan.ddd.generalservice.feature.address.Address;
import com.gokdenizozkan.ddd.generalservice.core.auditableentity.AuditableEntity;
import com.gokdenizozkan.ddd.generalservice.feature.legalentity.LegalEntity;
import com.gokdenizozkan.ddd.generalservice.feature.user.seller.Seller;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "stores")
@Getter
@Setter
@RequiredArgsConstructor
public class Store extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stores_gen")
    @SequenceGenerator(name = "stores_gen", sequenceName = "stores_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[\\d()+\\s]*$", message = "Phone number can contain only digits, spaces and parentheses")
    @Size(min = 2, max = 20, message = "Phone number must be between 2 and 20 characters")
    private String phone;

    @Column(name = "store_rating_average", nullable = false)
    private Float storeRatingAverage;
    @Column(name = "review_count", nullable = false)
    private Long reviewCount;

    @NotNull(message = "Store type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "store_type", nullable = false)
    private StoreType storeType;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "legal_entity_id", referencedColumnName = "id")
    private LegalEntity legalEntity;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, optional = false)
    private Address address;

    @OneToMany(mappedBy = "store", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Seller> sellers;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;
}
