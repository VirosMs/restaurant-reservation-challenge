package com.virosms.restaurantreservationchallenge.model.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response class for reservations, containing details about the reservation such as start and end time,
 * user name, table information, reservation ID, and a message.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationsResponse {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateStart;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateFinish;
    private String userName;
    private TablesDTO table;
    private Long reservationId;
    private String menssage;
    private String status;


    public String toString() {
        return "ReservationsResponse{" +
                "Hora inicio de la reserva =" + dateStart +
                "Hora fin de la reserva =" + dateFinish +
                ", userName='" + userName + '\'' +
                ", table=" + table +
                ", reservationI=" + reservationId +
                ", status='" + status + '\'' +
                ", menssage='" + menssage + '\'' +
                '}';
    }
}
