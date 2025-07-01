package com.virosms.restaurantreservationchallenge.model.User;


import com.virosms.restaurantreservationchallenge.model.email.Email;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entity representing a user in the restaurant reservation system.
 * This class implements UserDetails for Spring Security and maps to the "users" table in the database.
 * It contains fields for user details such as name, email, password, and role.
 */
@Entity(name = "users")
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Users implements UserDetails {

    @Id
    @JoinColumn(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "nombre")
    private String nombre;


    @Embedded
    private Email email;


    @JoinColumn(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    /**
     * Constructor for creating a user with the specified details.
     * Validates the email format before setting the fields.
     *
     * @param nombre   the name of the user
     * @param email    the email of the user (must be valid)
     * @param password the password of the user
     * @param role     the role of the user (ADMIN or CLIENT)
     */
    public Users(String nombre, Email email, String password, UserRole role) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the password of the user.
     *
     * @return the user's password
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_CLIENT"));
        else return List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
    }

    /**
     * Returns the password of the user.
     *
     * @return the user's password
     */
    @Override
    public String getUsername() {
        return this.email.getValue();
    }

    /**
     * Returns the password of the user.
     *
     * @return the user's password
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * Returns the password of the user.
     *
     * @return the user's password
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * Returns the password of the user.
     *
     * @return the user's password
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * Returns whether the user is enabled.
     *
     * @return true if the user is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
