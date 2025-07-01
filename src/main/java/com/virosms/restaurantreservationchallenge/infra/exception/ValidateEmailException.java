package com.virosms.restaurantreservationchallenge.infra.exception;

public class ValidateEmailException extends RuntimeException{


    public ValidateEmailException(String message) {
        super(message);
    }

    public ValidateEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateEmailException(Throwable cause) {
        super(cause);
    }
}
