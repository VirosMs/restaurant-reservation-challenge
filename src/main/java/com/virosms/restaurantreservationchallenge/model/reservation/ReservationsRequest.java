package com.virosms.restaurantreservationchallenge.model.reservation;

import com.virosms.restaurantreservationchallenge.model.Tables.RestaurantTables;
import com.virosms.restaurantreservationchallenge.model.User.Users;

import java.time.LocalDateTime;


public record ReservationsRequest(
        Users user,
        RestaurantTables table,
        LocalDateTime fechaReserva,
        Integer cantidadPersonas
) {

}