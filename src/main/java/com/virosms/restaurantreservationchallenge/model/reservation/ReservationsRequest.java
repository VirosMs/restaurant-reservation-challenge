package com.virosms.restaurantreservationchallenge.model.reservation;

import com.virosms.restaurantreservationchallenge.model.Tables.RestaurantTables;
import com.virosms.restaurantreservationchallenge.model.User.Users;

import java.time.LocalDateTime;

/**
 * Record representing a request to create a reservation.
 * This record contains the user making the reservation, the table being reserved,
 * the start time of the reservation, and the number of people for the reservation.
 */
public record ReservationsRequest(
        Users user,
        RestaurantTables table,
        LocalDateTime fechaReservaInicio,
        Integer cantidadPersonas
) {

}