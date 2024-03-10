package com.gokdenizozkan.ddd.config.exception;

public class NotActiveException extends RuntimeException {
    public NotActiveException(Class<?> clazz, Long id) {
        super(clazz.getSimpleName() + " with id " + id + " is not active. Either it is deleted or not enabled.");
    }
}
