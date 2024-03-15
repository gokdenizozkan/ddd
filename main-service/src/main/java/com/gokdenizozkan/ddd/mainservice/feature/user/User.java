package com.gokdenizozkan.ddd.mainservice.feature.user;

import com.gokdenizozkan.ddd.mainservice.core.auditableentity.AuditableEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Base class for all users.
 * It has the common properties for all users.
 */
@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class User extends AuditableEntity {
    @NotNull(message = "Name is required")
    protected String name;

    @Nullable
    protected String surname;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    protected String email;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[\\d()+\\s]*$", message = "Phone number can contain only digits, spaces and parentheses")
    @Size(min = 2, max = 20, message = "Phone number must be between 2 and 20 characters")
    protected String phone;

    @Nullable
    @Temporal(TemporalType.DATE)
    protected LocalDate birthdate;
}
