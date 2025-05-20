package com.virosms.restaurantreservationchallenge.infra.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}