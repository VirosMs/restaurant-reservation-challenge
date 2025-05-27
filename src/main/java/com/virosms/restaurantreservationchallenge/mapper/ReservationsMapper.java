package com.virosms.restaurantreservationchallenge.mapper;

import com.virosms.restaurantreservationchallenge.model.reservation.Reservations;
import com.virosms.restaurantreservationchallenge.model.reservation.ReservationsRequest;
import com.virosms.restaurantreservationchallenge.model.reservation.ReservationsResponse;
import com.virosms.restaurantreservationchallenge.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TablesMapper.class})
public interface ReservationsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "response.user")
    @Mapping(target = "restaurantTables", source = "response.table")
    @Mapping(target = "fechaReserva", source = "response.fechaReserva")
    Reservations mapToEntity(ReservationsRequest response);


    @Mapping(target = "menssage", constant = Constants.RESERVATION_CREATED_SUCCESS)
    @Mapping(target = "userName", source = "reservations.user.nombre")
    @Mapping(target = "table", source = "reservations.restaurantTables")
    @Mapping(target = "reservationId", source = "reservations.id")
    @Mapping(target = "date", source = "reservations.fechaReserva")
    ReservationsResponse mapToResponse(Reservations reservations);


}
