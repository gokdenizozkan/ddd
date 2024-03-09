package com.gokdenizozkan.ddd.feature.address;

import com.gokdenizozkan.ddd.core.auditableentity.AuditableEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addresses_gen")
    @SequenceGenerator(name = "addresses_gen", sequenceName = "addresses_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(precision = 9, scale = 6, nullable = false)
    private BigDecimal latitude;

    @NotNull
    @Column(precision = 9, scale = 6, nullable = false)
    private BigDecimal longitude;

    @NotNull
    @Size(min = 3, max = 3, message = "Country code must be 3 characters (ALPHA-3 CODE)")
    @Column(name = "country_code", length = 3)
    private String countryCode;

    @NotNull
    @Column(length = 60)
    private String city;

    @NotNull
    @Column(length = 60)
    private String region;

    @NotNull
    @Column(name = "address_line", length = 35)
    private String addressLine;

    @Nullable
    @Column(name = "address_details", length = 140)
    private String addressDetails;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[\\d()+\\s]*$", message = "Phone number can contain only digits, spaces and parentheses")
    @Size(min = 2, max = 20, message = "Phone number must be between 2 and 20 characters")
    @Column(name = "contact_phone", length = 50)
    private String contactPhone;
}
