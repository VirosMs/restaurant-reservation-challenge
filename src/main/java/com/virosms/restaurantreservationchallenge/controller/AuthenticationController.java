package com.virosms.restaurantreservationchallenge.controller;

import com.virosms.restaurantreservationchallenge.infra.security.TokenService;
import com.virosms.restaurantreservationchallenge.model.User.AuthenticationDTO;
import com.virosms.restaurantreservationchallenge.model.User.LoginResponseDTO;
import com.virosms.restaurantreservationchallenge.model.User.RegisterDTO;
import com.virosms.restaurantreservationchallenge.model.User.Users;
import com.virosms.restaurantreservationchallenge.service.AuthenticationService;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Este controlador maneja la autenticación de usuarios.
 * Proporciona endpoints para registrar y autenticar usuarios.
 */
@RestController
@EnableWebMvc
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    /**
     * Endpoint para registrar un nuevo usuario.
     * @param registerDTO Objeto que contiene la información del usuario a registrar.
     * @return Mensaje de éxito.
     */
    @PostMapping("/register")
    public ResponseEntity<String> registrarUsuario(@RequestBody @Valid RegisterDTO registerDTO) {
        return authenticationService.registrarUsuario(registerDTO);
    }


    /**
     * Endpoint para autenticar un usuario existente.
     * @return Mensaje de éxito.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUsuario(@RequestBody @Valid AuthenticationDTO data) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        var auth = authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((Users) auth.getPrincipal());

        return ResponseEntity.ok().body(new LoginResponseDTO(token));
    }
}
