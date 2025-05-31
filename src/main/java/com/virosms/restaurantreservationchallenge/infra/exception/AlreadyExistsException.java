package com.virosms.restaurantreservationchallenge.infra.exception;

/**
 * Exception thrown when an operation is attempted on an inactive table.
 * This could be due to the table being reserved, under maintenance, or otherwise unavailable.
 */
public class AlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new AlreadyExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public AlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new AlreadyExistsException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new AlreadyExistsException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
