package com.virosms.restaurantreservationchallenge.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Users {
    @Id
    @JoinColumn(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "nombre")
    private String nombre;

    @JoinColumn(name = "email")
    private String email;

    @JoinColumn(name = "password")
    private String password;

    @JoinColumn(name = "role")
    private Role role;

    public enum Role {
        CLIENT, ADMIN;


        /**
         * Returns the string representation of the role.
         *
         * @return the string representation of the role
         */
        public String getRole() {
            return this.name();
        }


        /**
         * Converts a string to a role enum.
         *
         * @param role the string representation of the role
         * @return the corresponding role enum
         * @throws IllegalArgumentException if the string does not match any role
         */
        public static Role fromString(String role) {
            return switch (role.toUpperCase()) {
                case "CLIENT" -> CLIENT;
                case "ADMIN" -> ADMIN;
                default -> throw new IllegalArgumentException("Invalid role: " + role);
            };
        }
    }
}
