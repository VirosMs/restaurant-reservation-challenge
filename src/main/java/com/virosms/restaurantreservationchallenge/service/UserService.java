package com.virosms.restaurantreservationchallenge.service;

import com.virosms.restaurantreservationchallenge.infra.exception.InvalidValueRequestException;
import com.virosms.restaurantreservationchallenge.infra.security.JwtUtils;
import com.virosms.restaurantreservationchallenge.model.User.UserDTO;
import com.virosms.restaurantreservationchallenge.model.User.Users;
import com.virosms.restaurantreservationchallenge.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


/**
 * Service class for managing user-related operations.
 * This class provides methods to retrieve user information from the request,
 * including the user's ID and email, using JWT authentication.
 */
@Service
@Validated
@Transactional
public class UserService {

    private final UsersRepository usersRepository;
    private final JwtUtils jwtUtils;

    /**
     * Constructor for UserService.
     *
     * @param usersRepository the repository for user data access
     * @param jwtUtils        utility class for handling JWT tokens
     */
    @Autowired
    public UserService(UsersRepository usersRepository, JwtUtils jwtUtils) {
        this.usersRepository = usersRepository;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Retrieves the user from the request based on the JWT token.
     *
     * @param request the HTTP request containing the JWT token
     * @return the user associated with the token
     * @throws InvalidValueRequestException if the token does not contain a valid email or if no user is found
     */
    public Users getUserFromRequest(HttpServletRequest request) {
        String userEmail = jwtUtils.getEmail(jwtUtils.getToken(request));

        if (userEmail == null || userEmail.isBlank()) {
            throw new InvalidValueRequestException("El token de autenticación no contiene un correo electrónico válido.");
        }

        Users user = usersRepository.findByEmail(userEmail);

        if (user == null) {
            throw new InvalidValueRequestException("No se encontró un usuario con el correo electrónico proporcionado.");
        }

        return user;
    }

    /**
     * Retrieves a UserDTO object containing user details from the request.
     *
     * @param request the HTTP request containing the JWT token
     * @return a UserDTO object with user ID, name, and email
     */
    public UserDTO getUserDTOFromRequest(HttpServletRequest request) {
        Users user = getUserFromRequest(request);
        return new UserDTO(user.getId(), user.getNombre(), user.getEmail());
    }

    /**
     * Retrieves the user ID from the request.
     *
     * @param request the HTTP request containing the JWT token
     * @return the ID of the user associated with the token
     */
    public Long getUserId(HttpServletRequest request) {
        Users user = getUserFromRequest(request);
        return user.getId();
    }
}
