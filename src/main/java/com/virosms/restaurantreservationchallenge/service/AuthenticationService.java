package com.virosms.restaurantreservationchallenge.service;

import com.virosms.restaurantreservationchallenge.exception.BadRequestException;
import com.virosms.restaurantreservationchallenge.model.User.RegisterDTO;
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

    private UsersRepository usersRepository;


    @Autowired
    AuthenticationService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public ResponseEntity registrarUsuario(RegisterDTO data) {
        logger.info("Registrando usuario INICIO");

        if (!Utils.validateUser(data)) {
            logger.error("Error en los datos de entrada del usuario: {}", data);
            throw new BadRequestException("Error en los datos de entrada del usuario");
        }

        System.out.println("data = " + data);

        try {
            boolean exists = this.usersRepository.existsByEmail(data.email());
            if (exists) {
                logger.error("Ya existe un usuario con ese email o nombre: {}", data);
                throw new BadRequestException("Ya existe un usuario con ese email o nombre");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
        System.out.println("data = " + data);

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        System.out.println("encryptedPassword = " + encryptedPassword);

        Users user = new Users(data.nombre(), data.email(), encryptedPassword, data.role());
        System.out.println("user = " + user);


        try{
            this.usersRepository.save(user);
        }catch (Exception e){
            logger.error("Error al guardar el usuario: {}", e.getMessage());
            throw new BadRequestException(e.getMessage());
        }

        logger.info("Registrando usuario FIN");
        return ResponseEntity.ok().build();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username);
    }
}
