package com.gokdenizozkan.ddd.generalservice.config.exception;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }

}
