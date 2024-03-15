package com.gokdenizozkan.ddd.mainservice.config.exception;

import com.gokdenizozkan.ddd.mainservice.config.response.Structured;
import com.gokdenizozkan.ddd.mainservice.config.response.StructuredResponseEntityBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Structured<Map<String, String>>> handleExceptionGlobal(Exception e, WebRequest request) {
        logException(e, request);
        return StructuredResponseEntityBuilder.<Map<String, String>>builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundWithIdException.class)
    public ResponseEntity<Structured<Map<String, String>>> handleIdNotFoundException(ResourceNotFoundWithIdException e, WebRequest request) {
        logException(e, request);
        return StructuredResponseEntityBuilder.<Map<String, String>>builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Structured<Map<String, String>>> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        logException(e, request);
        return StructuredResponseEntityBuilder.<Map<String, String>>builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Structured<Map<String, String>>> handleInvalidInputException(InvalidInputException e, WebRequest request) {
        logException(e, request);
        return StructuredResponseEntityBuilder.<Map<String, String>>builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of(
                        "path", request.getDescription(false),
                        "hint", "Please check the request body and query parameters for invalid input"
                ))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(ResourceNotActiveException.class)
    public ResponseEntity<Structured<Map<String, String>>> handleNotActiveException(ResourceNotActiveException e, WebRequest request) {
        logException(e, request);
        return StructuredResponseEntityBuilder.<Map<String, String>>builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Structured<Map<String, String>>> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        logException(e, request);
        return StructuredResponseEntityBuilder.<Map<String, String>>builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    public ResponseEntity<Structured<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        logException(e, request);
        return StructuredResponseEntityBuilder.<Map<String, String>>builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    private void logException(Exception e, WebRequest request) {
        log.error("Request at {} failed with exception: {}", request.getDescription(true), e.getMessage(), e);
    }
}
