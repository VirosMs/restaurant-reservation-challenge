package com.virosms.restaurantreservationchallenge.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.virosms.restaurantreservationchallenge.infra.exception.InvalidValueRequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * Utility class for handling JWT operations.
 * This class provides methods to extract email and token from JWT.
 */
@Component
public class JwtUtils {

    /**
     * Extracts the email from the JWT token.
     *
     * @param token the JWT token
     * @return the email extracted from the token
     * @throws InvalidValueRequestException if the token does not contain a valid subject
     */
    public String getEmail(String token) {
        DecodedJWT jwt = JWT.decode(token);
        if (jwt.getSubject() == null) {
            throw new InvalidValueRequestException("Token does not contain a valid subject.");
        }

        return jwt.getSubject();
    }

    /**
     * Extracts the JWT token from the Authorization header of the request.
     *
     * @param request the HTTP request containing the Authorization header
     * @return the extracted JWT token
     * @throws InvalidValueRequestException if the Authorization header is missing or invalid
     */
    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidValueRequestException("Authorization header is missing or invalid.");
        }

        authHeader = authHeader.substring(7);

        return authHeader;
    }
}
