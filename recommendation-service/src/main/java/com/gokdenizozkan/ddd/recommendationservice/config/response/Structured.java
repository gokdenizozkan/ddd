package com.gokdenizozkan.ddd.recommendationservice.config.response;

import jakarta.validation.constraints.NotNull;

public record Structured<T> (
        @NotNull
        boolean success,
        @NotNull
        String message,
        @NotNull
        T data
) {
    public static <T> Structured<T> of(boolean success, String message, T data) {
        return new Structured<>(success, message, data);
    }
}
