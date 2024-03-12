package com.gokdenizozkan.ddd.recommendationservice.config.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * ResponseTemplates can be used to create ResponseEntity<Structured<T>> objects with different HTTP status codes.<br>
 * By default, all responses have an immutable message corresponding the success status.<br>
 * Plus, the data field is converted to Structured<T> object, thus, present raw data.</T><br>
 * <br>
 * To write a custom message, please refer to the {@link StructuredResponseEntityBuilder} class.<br>
 */
public class ResponseTemplates {

    private static String actionResultOk = "request fulfilled";

    private ResponseTemplates() {
    }

    public static <T> ResponseEntity<Structured<T>> ok(T data) {
        return StructuredResponseEntityBuilder.<T>builder()
                .success(true)
                .message(actionResultOk)
                .data(data)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public static <T> ResponseEntity<Structured<T>> created(T data) {
        return StructuredResponseEntityBuilder.<T>builder()
                .success(true)
                .message(actionResultOk)
                .data(data)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public static ResponseEntity<Structured<Object>> noContent() {
        return StructuredResponseEntityBuilder.<Object>builder()
                .success(true)
                .message(actionResultOk)
                .data("Maybe real programming was the bugs we fixed along the way...")
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }
}
