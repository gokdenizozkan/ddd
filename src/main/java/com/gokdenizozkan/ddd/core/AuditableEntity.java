package com.gokdenizozkan.ddd.core;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor
public abstract class AuditableEntity {
    @CreationTimestamp(source = SourceType.DB)
    protected LocalDateTime createdAt;
    @CreationTimestamp(source = SourceType.DB)
    protected LocalDateTime lastModifiedAt;

    protected Boolean deleted;
    protected Boolean enabled;
}
