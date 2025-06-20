package com.virosms.restaurantreservationchallenge.infra.exception;

/**
 * Exception thrown when an operation is attempted on an inactive table.
 * This could be due to the table being reserved, under maintenance, or otherwise unavailable.
 */
public class NotExistException extends RuntimeException{
    /**
     * Constructs a new NotExistException with the specified detail message.
     *
     * @param message the detail message
     */
    public NotExistException(String message) {
        super(message);
    }

    /**
     * Constructs a new NotExistException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public NotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new NotExistException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public NotExistException(Throwable cause) {
        super(cause);
    }
}
