package com.gokdenizozkan.ddd.mainservice.config.exception;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }

}
