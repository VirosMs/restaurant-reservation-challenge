package com.virosms.restaurantreservationchallenge.mapper;

import com.virosms.restaurantreservationchallenge.model.reservation.Reservations;
import com.virosms.restaurantreservationchallenge.model.reservation.ReservationsRequest;
import com.virosms.restaurantreservationchallenge.model.reservation.ReservationsResponse;
import com.virosms.restaurantreservationchallenge.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;

/**
 * Mapper interface for converting between ReservationsRequest and Reservations entities,
 * as well as mapping Reservations entities to ReservationsResponse.
 * This interface uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring", uses = {TablesMapper.class})
public interface ReservationsMapper {

    /**
     * Maps a ReservationsRequest to an active Reservations entity.
     * The reservation's status is set to active, and the end date is calculated based on the start date.
     *
     * @param response the ReservationsRequest to map
     * @return a Reservations entity with the status set to active
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "restaurantTables", source = "table")
    @Mapping(target = "fechaReservaInicio", source = "fechaReservaInicio")
    @Mapping(target = "fechaReservaFin", source = "fechaReservaInicio", qualifiedByName = "mapDateFin")
    @Mapping(target = "status", expression = "java(mapStatus(com.virosms.restaurantreservationchallenge.utils.Constants.RESERVATION_STATUS_ACTIVE))")
    Reservations mapToEntityActive(ReservationsRequest response);


    /**
     * Maps a Reservations entity to a ReservationsResponse.
     * The response includes the reservation's ID, start and end dates, user name, and a success message.
     *
     * @param reservations the Reservations entity to map
     * @return a ReservationsResponse containing the reservation details
     */
    @Mapping(target = "menssage", constant = Constants.RESERVATION_CREATED_SUCCESS)
    @Mapping(target = "userName", source = "user.nombre")
    @Mapping(target = "table", source = "restaurantTables")
    @Mapping(target = "reservationId", source = "id")
    @Mapping(target = "dateStart", source = "fechaReservaInicio")
    @Mapping(target = "dateFinish", source = "fechaReservaFin")
    ReservationsResponse mapToResponse(Reservations reservations);

    /**
     * Maps a LocalDateTime to a LocalDateTime representing the end date of a reservation.
     * The end date is calculated by adding a predefined duration to the start date.
     *
     * @param date the start date of the reservation
     * @return the calculated end date of the reservation
     */
    @Named("mapDateFin")
    default LocalDateTime mapDateFin(LocalDateTime date) {
        return date.plusHours(Constants.RESERVATION_DURATION_HOURS);
    }


    /**
     * Maps a status string to a Reservations.Status enum.
     * This method converts the string representation of the status to the corresponding enum value.
     *
     * @param status the status string to map
     * @return the Reservations.Status enum corresponding to the status string
     */
    default Reservations.Status mapStatus(String status) {
        return Reservations.Status.fromString(status);
    }
}
