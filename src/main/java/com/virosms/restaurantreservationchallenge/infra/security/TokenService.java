package com.virosms.restaurantreservationchallenge.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.virosms.restaurantreservationchallenge.model.User.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Service for generating and validating JWT tokens for user authentication.
 * This service uses a secret key to sign the tokens and provides methods to generate
 * a token for a user and validate an existing token.
 */
@Service
public class TokenService {

    /**
     * Secret key used for signing the JWT tokens.
     * This value is injected from the application properties.
     */
    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Generates a JWT token for the specified user.
     * The token includes the user's email as the subject and an expiration date set to 2 hours from now.
     *
     * @param user the user for whom the token is generated
     * @return a signed JWT token as a String
     */
    public String generateToken(Users user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("restaurant-reservation-challenge")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExperirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    /**
     * Validates the provided JWT token.
     * If the token is valid, it returns the subject (user's email) of the token.
     * If the token is invalid or expired, it returns an empty string.
     *
     * @param token the JWT token to validate
     * @return the user's email if the token is valid, otherwise an empty string
     */
    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("restaurant-reservation-challenge")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    /**
     * Generates an expiration date for the JWT token.
     * The expiration date is set to 2 hours from the current time.
     *
     * @return an Instant representing the expiration date
     */
    private Instant genExperirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("+02:00"));
    }
}
