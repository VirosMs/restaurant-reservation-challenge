package com.virosms.restaurantreservationchallenge.infra.exception;

/**
 * Exception thrown when an operation is attempted on an inactive table.
 * This could be due to the table being reserved, under maintenance, or otherwise unavailable.
 */
public class BadRequestException extends RuntimeException {
    /**
     * Constructs a new BadRequestException with the specified detail message.
     *
     * @param mensaje the detail message
     */
    public BadRequestException(String mensaje) {
        super(mensaje);
    }
}
