package com.virosms.restaurantreservationchallenge.infra.security;

import com.virosms.restaurantreservationchallenge.model.email.Email;
import com.virosms.restaurantreservationchallenge.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Security filter that intercepts requests to validate JWT tokens and set the authentication context.
 * This filter is applied to every request to ensure that the user is authenticated.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    /**
     * Repository for accessing user data.
     */
    private final UsersRepository usersRepository;

    /**
     * Service for validating JWT tokens.
     */
    private final TokenService tokenService;

    /**
     * Constructs a SecurityFilter with the specified UsersRepository and TokenService.
     *
     * @param usersRepository the repository for user details
     * @param tokenService    the service for validating JWT tokens
     */
    @Autowired
    public SecurityFilter(UsersRepository usersRepository, TokenService tokenService) {
        this.usersRepository = usersRepository;
        this.tokenService = tokenService;
    }

    /**
     * Filters incoming requests to check for a valid JWT token and set the authentication context.
     * If a valid token is found, the user details are retrieved and set in the security context.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to continue processing the request
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null) {
            var email = tokenService.validateToken(token);
            if (email != null) {
                UserDetails userDetails = usersRepository.findByEmail(new Email(email));
                if (userDetails != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Recovers the JWT token from the Authorization header of the request.
     *
     * @param request the HTTP request
     * @return the JWT token if present, or null if not found
     */
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank()) return null;
        return authHeader.replace("Bearer ", "");
    }
}
