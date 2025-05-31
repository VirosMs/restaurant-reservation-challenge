package com.virosms.restaurantreservationchallenge.infra.exception;

/**
 * Exception thrown when an operation is attempted on an inactive table.
 * This could be due to the table being reserved, under maintenance, or otherwise unavailable.
 */
public class ForbiddenException extends RuntimeException {
    /**
     * Constructs a new ForbiddenException with the specified detail message.
     *
     * @param message the detail message
     */
    public ForbiddenException(String message) {
        super(message);
    }
}