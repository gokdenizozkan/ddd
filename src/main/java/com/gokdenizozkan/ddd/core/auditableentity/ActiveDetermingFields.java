package com.gokdenizozkan.ddd.core.auditableentity;

import com.gokdenizozkan.ddd.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.config.quality.BaseRepository;

import java.util.function.Supplier;

public record ActiveDetermingFields(
        Boolean deleted,
        Boolean enabled
) {
    /**
     * An internal API to create an instance of ActiveDetermingFields.<br>
     * @param id the id of the entity
     * @param repository the repository of the entity
     * @param entityClass the class of the repository/original entity
     * @return an instance of ActiveDetermingFields
     * @param <ID> the type of the id
     * @param <T> the type of the entity
     * @param <R> the type of the repository
     * @throws ResourceNotFoundWithIdException if the entity with the given id is not found
     */
    public static <ID, T, R extends BaseRepository<T, ID>> ActiveDetermingFields of(ID id, R repository, Class<T> entityClass) {
        return repository.findActiveDetermingFields(id)
                .orElseThrow(() -> new ResourceNotFoundWithIdException(entityClass, id));
    }

    public static boolean isActive(Boolean deleted, Boolean enabled) {
        return !deleted && enabled;
    }

    public boolean isActive() {
        return !deleted && enabled;
    }

    public boolean isNotActive() {
        return deleted || !enabled;
    }

    /**
     * An internal API to throw an exception if the entity is not active.<br>
     * @param exceptionSupplier the supplier of the exception
     * @param <X> the type of the exception
     * @throws X if the entity is not active
     */
    public <X extends Throwable> void ifNotActiveThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (isNotActive()) {
            throw (X) exceptionSupplier.get();
        }
    }

    public <T extends AuditableEntity> void copyTo(T entity) {
        entity.setDeleted(deleted);
        entity.setEnabled(enabled);
    }
}
