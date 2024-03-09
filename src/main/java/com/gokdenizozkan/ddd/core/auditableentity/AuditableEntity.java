package com.gokdenizozkan.ddd.core.auditableentity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor
public abstract class AuditableEntity {
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "last_modified_at", nullable = false)
    private LocalDateTime lastModifiedAt;

    private Boolean deleted;
    private Boolean enabled;

    @PrePersist
    public void initializeDeletedAndEnabled() {
        deleted = deleted == null ? false : deleted;
        enabled = enabled == null ? true : enabled;
    }
}
