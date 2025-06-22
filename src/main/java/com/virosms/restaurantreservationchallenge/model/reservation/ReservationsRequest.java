package com.virosms.restaurantreservationchallenge.model.reservation;

import com.virosms.restaurantreservationchallenge.model.Tables.RestaurantTables;

import java.time.LocalDateTime;

/**
 * Record representing a request to create a reservation.
 * This record contains the user making the reservation, the table being reserved,
 * the start time of the reservation, and the number of people for the reservation.
 */
public record ReservationsRequest(
        RestaurantTables table,
        LocalDateTime fechaReservaInicio,
        Integer cantidadPersonas
) {

}