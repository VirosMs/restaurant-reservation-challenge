package com.virosms.restaurantreservationchallenge.model.User;

/**
 * Data Transfer Object (DTO) for User entity.
 * This class is used to transfer user data between layers of the application.
 * It contains fields for user ID, name, and email.
 */
public record UserDTO(Long id, String nombre, String email) {
}
