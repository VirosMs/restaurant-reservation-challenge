package com.virosms.restaurantreservationchallenge.model.User;

/**
 * Record representing the response for a user login operation.
 * This record contains a token that is typically used for authentication in subsequent requests.
 */
public record LoginResponseDTO(
        String token
) {
}
