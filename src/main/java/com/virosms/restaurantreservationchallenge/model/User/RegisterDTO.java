package com.virosms.restaurantreservationchallenge.model.User;

/**
 * Record representing the authentication details of a user.
 * This record contains the user's email and password.
 */
public record RegisterDTO(
        String nombre,
        String email,
        String password
) {
}
