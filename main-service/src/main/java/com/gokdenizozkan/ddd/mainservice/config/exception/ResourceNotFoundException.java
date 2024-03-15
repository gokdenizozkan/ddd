package com.gokdenizozkan.ddd.mainservice.config.exception;

public class ResourceNotFoundException extends RuntimeException {

    public <T, ID> ResourceNotFoundException(Class<T> clazz, ID id) {
        super(new StringBuilder(clazz.getSimpleName()).append(" with id ").append(id).append(" not found").toString());
    }
}
