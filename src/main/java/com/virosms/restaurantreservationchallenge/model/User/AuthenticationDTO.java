package com.virosms.restaurantreservationchallenge.model.User;


public record AuthenticationDTO(
        String email,
        String password
) {
}
