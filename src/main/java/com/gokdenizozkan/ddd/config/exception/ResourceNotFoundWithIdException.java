package com.gokdenizozkan.ddd.config.exception;

public class ResourceNotFoundWithIdException extends RuntimeException {
    public ResourceNotFoundWithIdException(String message) {
        super(message);
    }

    public <T, ID> ResourceNotFoundWithIdException(Class<T> clazz, ID id) {
        super(new StringBuilder(clazz.getSimpleName()).append(" with id ").append(id).append(" not found").toString());
    }
}
