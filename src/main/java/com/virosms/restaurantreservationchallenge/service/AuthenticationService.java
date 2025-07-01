package com.virosms.restaurantreservationchallenge.service;

import com.virosms.restaurantreservationchallenge.infra.exception.BadRequestException;
import com.virosms.restaurantreservationchallenge.model.email.Email;
import com.virosms.restaurantreservationchallenge.model.User.RegisterDTO;
import com.virosms.restaurantreservationchallenge.model.User.UserRole;
import com.virosms.restaurantreservationchallenge.model.User.Users;
import com.virosms.restaurantreservationchallenge.repository.UsersRepository;
import com.virosms.restaurantreservationchallenge.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/* * AuthenticationService is a service class that handles user authentication and registration.
 * It implements UserDetailsService to load user details by username.
 */
@Service
@Transactional
@Validated
public class AuthenticationService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(AuthenticationService.class);

    private final UsersRepository usersRepository;


    /**
     * Constructor for AuthenticationService.
     * * @param usersRepository the repository for managing Users
     * * This constructor is used to inject the UsersRepository dependency.
     * @param usersRepository the repository for managing Users
     */
    @Autowired
    AuthenticationService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Registers a new user with the provided data.
     *
     * @param data the registration data containing user details
     * @return a ResponseEntity indicating the result of the registration
     * @throws BadRequestException if the input data is invalid or if a user with the same email or name already exists
     */
    public ResponseEntity<String> registrarUsuario(RegisterDTO data) {
        logger.info("Registrando usuario INICIO");


        if (!Utils.validateUser(data)) {
            logger.error("Error en los datos de entrada del usuario: {}", data);
            throw new BadRequestException("Error en los datos de entrada del usuario");
        }

        try {
            System.out.println("Validando email: " + data.email().getValue() + " getType: " + data.email().getClass().getSimpleName());
            String emailValue = data.email().getValue();
            System.out.println("emailValue: " + emailValue);
            boolean exists = this.usersRepository.existsByEmail(data.email());
            if (exists) {
                logger.error("Ya existe un usuario con ese email o nombre: {}", data);
                throw new BadRequestException("Ya existe un usuario con ese email o nombre");
            }
            System.out.println("Validando email: " + data.email().getValue() + " getType: " + data.email().getClass().getSimpleName() + " - Validado base de datos");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        Users user = new Users(data.nombre(), data.email(), encryptedPassword, UserRole.CLIENT);

        try {
            this.usersRepository.save(user);
        } catch (Exception e) {
            logger.error("Error al guardar el usuario: {}", e.getMessage());
            throw new BadRequestException(e.getMessage());
        }

        logger.info("Registrando usuario FIN");
        return ResponseEntity.ok().body("¡Usuario registrado exitosamente! <3 Bienvenido a la plataforma.");
    }

    /* * Loads user details by username.
     *
     * @param username the username of the user
     * @return UserDetails of the user with the specified username
     * @throws UsernameNotFoundException if no user with the specified username is found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(new Email(username));
    }
}
