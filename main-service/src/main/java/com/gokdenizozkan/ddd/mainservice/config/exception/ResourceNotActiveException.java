package com.gokdenizozkan.ddd.mainservice.config.exception;

public class ResourceNotActiveException extends RuntimeException {
    public <T, ID> ResourceNotActiveException(Class<T> clazz, ID id) {
        super(new StringBuilder(clazz.getSimpleName()).append(" with id ").append(id).append(" is not active").toString());
    }
}
