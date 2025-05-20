package com.virosms.restaurantreservationchallenge.model.User;


public record RegisterDTO(
        String nombre,
        String email,
        String password
) {
}
