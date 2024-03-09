package com.gokdenizozkan.ddd.core.auditableentity;

public record ActiveDetermingFields(
        Boolean deleted,
        Boolean enabled
) {
    public boolean isActive() {
        return !deleted && enabled;
    }

    public <T extends AuditableEntity> T copyTo(T entity) {
        entity.setDeleted(deleted);
        entity.setEnabled(enabled);
        return entity;
    }
}
