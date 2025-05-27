package com.virosms.restaurantreservationchallenge.model.reservation;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.virosms.restaurantreservationchallenge.model.Tables.RestaurantTables;
import com.virosms.restaurantreservationchallenge.model.User.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id", referencedColumnName = "id", nullable = false)
    private RestaurantTables restaurantTables;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users user;


    @Column(name = "fecha_reserva")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaReserva;

    @Column(name = "cantidad_personas", nullable = false)
    private int cantidadPersonas;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Reservations(Users users, RestaurantTables restaurantTables, LocalDateTime date, Integer cantidadPersonas, Status status) {
        this.user = users;
        this.restaurantTables = restaurantTables;
        this.fechaReserva = date;
        this.cantidadPersonas = cantidadPersonas;
        this.status = status;
    }

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
