package com.gokdenizozkan.ddd.config.response;

public class StructuredResponseBuilder <T> {
    private boolean success;
    private String message;
    private T data;

    private StructuredResponseBuilder() {
    }

    public static <T> StructuredResponseBuilder<T> builder() {
        return new StructuredResponseBuilder<>();
    }

    public StructuredResponseBuilder<T> success(boolean success) {
        this.success = success;
        return this;
    }

    public StructuredResponseBuilder<T> message(String message) {
        this.message = message;
        return this;
    }

    public StructuredResponseBuilder<T> data(T data) {
        this.data = data;
        return this;
    }

    public Structured<T> build() {
        return new Structured<>(success, message, data);
    }
}
