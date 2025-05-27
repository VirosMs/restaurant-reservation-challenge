package com.virosms.restaurantreservationchallenge.model.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationsResponse {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
    private String userName;
    private TablesDTO table;
    private Long reservationId;
    private String menssage;


    public String toString() {
        return "ReservationsResponse{" +
                "date=" + date +
                ", userName='" + userName + '\'' +
                ", table=" + table +
                ", reservationI=" + reservationId +
                ", menssage='" + menssage + '\'' +
                '}';
    }
}
