package com.virosms.restaurantreservationchallenge.utils;

/**
 * Constants class contains various constants used throughout the application.
 * It includes regex patterns, reservation durations, status messages, and error messages.
 */
public class Constants {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final int RESERVATION_DURATION_HOURS = 1;

    // Enums
    public static final String RESERVATION_STATUS_ACTIVE = "ACTIVO";
    public static final String RESERVATION_STATUS_CANCELLED = "CANCELADO";
    public static final String TABLE_STATUS_AVAILABLE = "DISPONIBLE";
    public static final String TABLE_STATUS_INACTIVE = "INACTIVA";
    public static final String TABLE_STATUS_RESERVED = "RESERVADO";


    // Success messages
    public static final String RESERVATION_CREATED_SUCCESS = "Reserva creada con éxito.";


    // Error messages
    public static final String INVALID_EMAIL_FORMAT = "El formato del correo electrónico es inválido.";
    public static final String INVALID_PHONE_FORMAT = "El formato del número de teléfono es inválido.";
    public static final String INVALID_DATE_FORMAT = "El formato de la fecha es inválido.";
    public static final String INVALID_RESERVATION_REQUEST = "Los datos de la reserva son inválidos o faltan campos obligatorios.";
    public static final String TABLE_NOT_AVAILABLE = "La mesa no está disponible para la fecha y hora solicitadas.";
    public static final String TABLE_CAPACITY_EXCEEDED = "La capacidad de la mesa no es suficiente para la cantidad de personas solicitadas. Por favor, elige una mesa con mayor capacidad o haga dos reservas.";
    public static final String RESERVATION_CREATION_FAILED = "Error al crear la reserva. Por favor, inténtalo de nuevo más tarde.";

    public static final String INVALID_USER_DATA = "Los datos del usuario son inválidos o faltan campos obligatorios.";
}
