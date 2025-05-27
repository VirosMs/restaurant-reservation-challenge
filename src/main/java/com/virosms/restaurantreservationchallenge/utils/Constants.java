package com.virosms.restaurantreservationchallenge.utils;

public class Constants {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

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
}
