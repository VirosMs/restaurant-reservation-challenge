package com.virosms.restaurantreservationchallenge.model.Tables;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity(name = "tables")
@Table(name = "tables")
@NoArgsConstructor
@AllArgsConstructor
public class Tables {

    @Id
    @JoinColumn(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nombre;


    private Integer capacidad;


    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        DISPONIBLE, RESERVADA, INACTIVA;

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
                case "INACTIVA" -> INACTIVA;
                default -> throw new IllegalArgumentException("Invalid status: " + status);
            };
        }

        /**
         * Validates if the given status is a valid enum.
         *
         * @param status the status to validate
         * @return true if the status is valid, false otherwise
         */
        public static boolean validateStatus(Status status) {
            if (status == null) return false;
            for (Status s : Status.values()) {
                if (s == status) return true;
            }
            return false;
        }

        /**
         * Compares two status enums for equality.
         *
         * @param s1 the first status enum
         * @param s2 the second status enum
         * @return true if both statuses are equal, false otherwise
         */
        public static boolean equals(Status s1, Status s2) {
            if (s1 == null && s2 == null) {
                return true;
            }
            if (s1 == null || s2 == null) {
                return false;
            }
            return s1.name().equals(s2.name());
        }
    }
}
