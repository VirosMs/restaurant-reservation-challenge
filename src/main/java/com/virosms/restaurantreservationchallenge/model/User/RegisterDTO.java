package com.virosms.restaurantreservationchallenge.model.User;

import com.virosms.restaurantreservationchallenge.model.email.Email;

/**
 * Record representing the authentication details of a user.
 * This record contains the user's email and password.
 */
public record RegisterDTO(
        String nombre,
        Email email,
        String password
) {
}
