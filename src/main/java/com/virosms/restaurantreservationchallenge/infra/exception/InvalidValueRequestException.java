package com.virosms.restaurantreservationchallenge.infra.exception;

public class InvalidValueRequestException extends RuntimeException{
    public InvalidValueRequestException(String mensaje) {
        super(mensaje);
    }
}
