package com.gokdenizozkan.ddd.config.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StructuredResponseEntityBuilder <T> {
    private boolean success;
    private String message;
    private T data;
    private HttpStatus httpStatus;

    private StructuredResponseEntityBuilder() {
    }

    public static <T> StructuredResponseEntityBuilder<T> builder() {
        return new StructuredResponseEntityBuilder<>();
    }

    public StructuredResponseEntityBuilder<T> success(boolean success) {
        this.success = success;
        return this;
    }

    public StructuredResponseEntityBuilder<T> message(String message) {
        this.message = message;
        return this;
    }

    public StructuredResponseEntityBuilder<T> data(T data) {
        this.data = data;
        return this;
    }

    public StructuredResponseEntityBuilder<T> httpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public ResponseEntity<Structured<T>> build() {
        return ResponseEntity.status(httpStatus).body(new Structured<T>(success, message, data));
    }
}
