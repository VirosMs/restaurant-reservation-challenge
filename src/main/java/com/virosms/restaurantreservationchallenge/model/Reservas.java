package com.virosms.restaurantreservationchallenge.model;


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
    private Mesas mesaId;

    @JoinColumn(name = "data_reserva")
    //@Temporal(TemporalType.TIMESTAMP)
    private Date fechaReserva;

    @JoinColumn(name = "status")
    private String status;
}
