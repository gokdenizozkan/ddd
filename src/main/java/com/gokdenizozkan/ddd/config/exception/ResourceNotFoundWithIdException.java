package com.gokdenizozkan.ddd.config.exception;

public class ResourceNotFoundWithIdException extends RuntimeException {
    public ResourceNotFoundWithIdException(String message) {
        super(message);
    }

    public ResourceNotFoundWithIdException(Class<?> clazz, Long id) {
        super(String.format("%s with id %d not found", clazz.getSimpleName(), id));
    }

    public ResourceNotFoundWithIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundWithIdException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundWithIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
