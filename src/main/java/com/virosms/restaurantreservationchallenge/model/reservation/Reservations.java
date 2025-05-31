package com.virosms.restaurantreservationchallenge.model.reservation;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.virosms.restaurantreservationchallenge.model.Tables.RestaurantTables;
import com.virosms.restaurantreservationchallenge.model.User.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a reservation in the restaurant reservation system.
 * This class maps to the "reservations" table in the database and contains
 * fields for reservation details such as start and end time, number of people,
 * status, and relationships with users and restaurant tables.
 */
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


    @Column(name = "fecha_reserva_inicio", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaReservaInicio;

    @Column(name = "fecha_reserva_fin", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaReservaFin;

    @Column(name = "cantidad_personas", nullable = false)
    private int cantidadPersonas;

    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Constructor for creating a reservation with all necessary fields.
     *
     * @param users                the user making the reservation
     * @param restaurantTables     the table being reserved
     * @param fechaReservaInicio   the start time of the reservation
     * @param fechaReservaFin      the end time of the reservation
     * @param cantidadPersonas     the number of people for the reservation
     * @param status               the status of the reservation (active or cancelled)
     */
    public Reservations(Users users, RestaurantTables restaurantTables, LocalDateTime fechaReservaInicio,  LocalDateTime fechaReservaFin, Integer cantidadPersonas, Status status) {
        this.user = users;
        this.restaurantTables = restaurantTables;
        this.fechaReservaInicio = fechaReservaInicio;
        this.fechaReservaFin = fechaReservaFin;
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
