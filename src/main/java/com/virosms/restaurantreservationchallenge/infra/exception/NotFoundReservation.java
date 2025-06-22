package com.virosms.restaurantreservationchallenge.infra.exception;

public class NotFoundReservation extends RuntimeException{
    /**
     * Constructs a new NotFoundReservation with the specified detail message.
     *
     * @param message the detail message
     */
    public NotFoundReservation(String message) {
        super(message);
    }

    /**
     * Constructs a new NotFoundReservation with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public NotFoundReservation(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new NotFoundReservation with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public NotFoundReservation(Throwable cause) {
        super(cause);
    }
}
