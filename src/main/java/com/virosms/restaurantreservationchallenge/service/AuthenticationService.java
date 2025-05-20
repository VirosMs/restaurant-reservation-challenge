package com.virosms.restaurantreservationchallenge.service;

import com.virosms.restaurantreservationchallenge.exception.BadRequestException;
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

@Service
@Transactional
@Validated
public class AuthenticationService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(AuthenticationService.class);

    private final UsersRepository usersRepository;


    @Autowired
    AuthenticationService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public ResponseEntity<String> registrarUsuario(RegisterDTO data) {
        logger.info("Registrando usuario INICIO");

        if (!Utils.validateUser(data)) {
            logger.error("Error en los datos de entrada del usuario: {}", data);
            throw new BadRequestException("Error en los datos de entrada del usuario");
        }

        try {
            boolean exists = this.usersRepository.existsByEmail(data.email());
            if (exists) {
                logger.error("Ya existe un usuario con ese email o nombre: {}", data);
                throw new BadRequestException("Ya existe un usuario con ese email o nombre");
            }
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username);
    }
}
