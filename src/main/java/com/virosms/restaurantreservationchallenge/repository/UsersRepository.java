package com.virosms.restaurantreservationchallenge.repository;

import com.virosms.restaurantreservationchallenge.model.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);

    UserDetails findByEmail(String email);
}
