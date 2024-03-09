package com.gokdenizozkan.ddd.config.exception;

import com.gokdenizozkan.ddd.config.response.Structured;
import com.gokdenizozkan.ddd.config.response.StructuredResponseEntityBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Structured<Object>> handleExceptionGlobal(Exception e, WebRequest request) {
        return StructuredResponseEntityBuilder.builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundWithIdException.class)
    public ResponseEntity<Structured<Object>> handleIdNotFoundException(ResourceNotFoundWithIdException e, WebRequest request) {
        return StructuredResponseEntityBuilder.builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Structured<Object>> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        return StructuredResponseEntityBuilder.builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Structured<Object>> handleInvalidInputException(InvalidInputException e, WebRequest request) {
        return StructuredResponseEntityBuilder.builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
}
