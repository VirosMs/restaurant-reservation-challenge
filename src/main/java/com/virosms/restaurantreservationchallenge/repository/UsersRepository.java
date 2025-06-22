package com.virosms.restaurantreservationchallenge.repository;

import com.virosms.restaurantreservationchallenge.model.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 * UsersRepository is an interface for managing Users in the database.
 * It extends JpaRepository to provide basic CRUD operations and custom queries.
 */
@Repository
@EnableJpaRepositories
public interface UsersRepository extends JpaRepository<Users, Long> {

    /**
     * Checks if a user exists by their email.
     *
     * @param email the email of the user
     * @return true if a user with the specified email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user
     * @return the UserDetails of the user with the specified email
     */
    Users findByEmail(String email);


}
