package com.virosms.restaurantreservationchallenge.infra.exception;

/**
 * Exception thrown when an operation is attempted on an inactive table.
 * This could be due to the table being reserved, under maintenance, or otherwise unavailable.
 */
public class TableNotAvailableException extends RuntimeException{
    /**
     * Constructs a new TableNotAvailableException with the specified detail message.
     *
     * @param message the detail message
     */
    public TableNotAvailableException(String message) {
        super(message);
    }
}
