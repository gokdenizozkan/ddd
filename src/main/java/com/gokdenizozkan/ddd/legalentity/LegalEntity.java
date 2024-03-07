package com.gokdenizozkan.ddd.legalentity;

import com.gokdenizozkan.ddd.address.Address;
import com.gokdenizozkan.ddd.core.AuditableEntity;
import com.gokdenizozkan.ddd.store.Store;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "legal_entities")
@Getter
@Setter
@RequiredArgsConstructor
public class LegalEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "legal_entities_gen")
    @SequenceGenerator(name = "legal_entities_gen", sequenceName = "legal_entities_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "Legal name is required")
    @Column(name = "legal_name", nullable = false)
    private String legalName;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[\\d()+\\s]*$", message = "Phone number can contain only digits, spaces and parentheses")
    @Size(min = 2, max = 20, message = "Phone number must be between 2 and 20 characters")
    private String phone;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Address address;

    @OneToMany(mappedBy = "legalEntity")
    private List<Store> stores;
}
