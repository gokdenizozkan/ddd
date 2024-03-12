package com.gokdenizozkan.ddd.generalservice.config.exception;

import com.gokdenizozkan.ddd.generalservice.config.response.Structured;
import com.gokdenizozkan.ddd.generalservice.config.response.StructuredResponseEntityBuilder;
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
    public ResponseEntity<Structured<Map<String, String>>> handleExceptionGlobal(Exception e, WebRequest request) {
        return StructuredResponseEntityBuilder.<Map<String, String>>builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundWithIdException.class)
    public ResponseEntity<Structured<Map<String, String>>> handleIdNotFoundException(ResourceNotFoundWithIdException e, WebRequest request) {
        return StructuredResponseEntityBuilder.<Map<String, String>>builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Structured<Map<String, String>>> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        return StructuredResponseEntityBuilder.<Map<String, String>>builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Structured<Map<String, String>>> handleInvalidInputException(InvalidInputException e, WebRequest request) {
        return StructuredResponseEntityBuilder.<Map<String, String>>builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(ResourceNotActiveException.class)
    public ResponseEntity<Structured<Map<String, String>>> handleNotActiveException(ResourceNotActiveException e, WebRequest request) {
        return StructuredResponseEntityBuilder.<Map<String, String>>builder()
                .success(false)
                .message(e.getMessage())
                .data(Map.of("path", request.getDescription(false)))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
}
