package com.virosms.restaurantreservationchallenge.model.Tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity representing a restaurant table.
 * This class maps to the "restaurant_tables" table in the database and contains
 * fields for table details such as name, capacity, and status.
 */
@Data
@Entity
@Table(name = "restaurant_tables")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RestaurantTables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    private Status status;


    /**
     * Constructor for creating a restaurant table with all necessary fields.
     */
    public enum Status {
        DISPONIBLE, RESERVADA, INACTIVA;

        /**
         * Converts a string to a Status enum.
         *
         * @param status the string representation of the status
         * @return the corresponding Status enum
         * @throws IllegalArgumentException if the string does not match any Status
         */
        public static Status fromString(String status) {
            return switch (status.toUpperCase()) {
                case "DISPONIBLE" -> DISPONIBLE;
                case "RESERVADA" -> RESERVADA;
                case "INACTIVA" -> INACTIVA;
                default -> throw new IllegalArgumentException("Invalid status: " + status);
            };
        }

        /**
         * Validates if the provided status is a valid Status enum.
         *
         * @param status the Status enum to validate
         * @return true if valid, false otherwise
         */
        public static boolean validateStatus(Status status) {
            if (status == null) return false;
            for (Status s : Status.values()) {
                if (s == status) return true;
            }
            return false;
        }

        /**
         * Compares two Status enums for equality.
         *
         * @param s1 the first Status enum
         * @param s2 the second Status enum
         * @return true if both are equal, false otherwise
         */
        public static boolean equals(Status s1, Status s2) {
            if (s1 == null && s2 == null) return true;
            if (s1 == null || s2 == null) return false;
            return s1.name().equals(s2.name());
        }

        public String getStatus() {
            return this.name();
        }
    }
}
