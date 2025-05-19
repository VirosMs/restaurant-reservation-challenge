package com.virosms.restaurantreservationchallenge.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Mesas {

    @Id
    @JoinColumn(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "nombre")
    private String nombre;

    @JoinColumn(name = "capacidad")
    private Integer capacidad;

    @JoinColumn(name = "status")
    private Status status;

    public enum Status {
        DISPONIBLE, RESERVADA, CANCELADA;

        /**
         * Returns the string representation of the status.
         *
         * @return the string representation of the status
         */
        public String getStatus() {
            return this.name();
        }

        /**
         * Converts a string to a status enum.
         *
         * @param status the string representation of the status
         * @return the corresponding status enum
         * @throws IllegalArgumentException if the string does not match any status
         */
        public static Status fromString(String status) {
            return switch (status.toUpperCase()) {
                case "DISPONIBLE" -> DISPONIBLE;
                case "RESERVADA" -> RESERVADA;
                case "CANCELADA" -> CANCELADA;
                default -> throw new IllegalArgumentException("Invalid status: " + status);
            };
        }
    }
}
