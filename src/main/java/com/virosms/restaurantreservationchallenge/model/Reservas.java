package com.virosms.restaurantreservationchallenge.model;


import com.virosms.restaurantreservationchallenge.model.Tables.Tables;
import com.virosms.restaurantreservationchallenge.model.User.Users;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Reservas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Tables mesaId;

    @JoinColumn(name = "fecha_reserva")
    //@Temporal(TemporalType.TIMESTAMP)
    private Date fechaReserva;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        ACTIVO, CANCELADO;

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
                case "ACTIVO" -> ACTIVO;
                case "CANCELADO" -> CANCELADO;
                default -> throw new IllegalArgumentException("Invalid status: " + status);
            };
        }
    }
}
