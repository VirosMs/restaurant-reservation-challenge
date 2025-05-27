package com.virosms.restaurantreservationchallenge.infra.exception;

public class TableNotAvailableException extends RuntimeException{
    public TableNotAvailableException(String message) {
        super(message);
    }
}
