package com.virosms.restaurantreservationchallenge.service;

import com.virosms.restaurantreservationchallenge.infra.exception.InvalidValueRequestException;
import com.virosms.restaurantreservationchallenge.infra.exception.TableNotAvailableException;
import com.virosms.restaurantreservationchallenge.mapper.ReservationsMapper;
import com.virosms.restaurantreservationchallenge.model.reservation.Reservations;
import com.virosms.restaurantreservationchallenge.model.reservation.ReservationsRequest;
import com.virosms.restaurantreservationchallenge.model.reservation.ReservationsResponse;
import com.virosms.restaurantreservationchallenge.repository.ReservationRepository;
import com.virosms.restaurantreservationchallenge.utils.Constants;
import com.virosms.restaurantreservationchallenge.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Validated
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TablesService tablesService;
    private final ReservationsMapper reservationMapper;
    Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, TablesService tablesService, @Qualifier("reservationsMapperImpl") ReservationsMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.tablesService = tablesService;
        this.reservationMapper = reservationMapper;
    }


    /**
     * Método para crear una nueva reserva.
     *
     * @param reservationsRequest Datos de la reserva a crear.
     * @return Respuesta con el mensaje de éxito y los datos de la reserva creada.
     */
    public ResponseEntity<?> createReservation(ReservationsRequest reservationsRequest) {

        if (Utils.validateRequest(reservationsRequest)) {
            throw new InvalidValueRequestException(Constants.INVALID_RESERVATION_REQUEST);
        }

        if (!isTableAvailable(reservationsRequest.table().getId(), reservationsRequest.fechaReserva())) {
            throw new TableNotAvailableException(Constants.TABLE_NOT_AVAILABLE);
        }

        if (!tablesService.isAvailableCapacity(reservationsRequest.table().getId(), reservationsRequest.cantidadPersonas())) {
            throw new InvalidValueRequestException(Constants.TABLE_CAPACITY_EXCEEDED);
        }

        try {
            Reservations reservation = reservationMapper.mapToEntity(reservationsRequest);

            reservation.setStatus(Reservations.Status.ACTIVO);
            System.out.println("Creating reservation: " + reservation);
            Reservations savedReservation = reservationRepository.save(reservation);
            System.out.println("Saved reservation: " + savedReservation);
            Optional<Reservations> fullReservation = reservationRepository.findByIdWithUserAndTable(savedReservation.getId());
            System.out.println("Full Reservation: " + fullReservation);
            ReservationsResponse response = reservationMapper.mapToResponse(fullReservation.get());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new InvalidValueRequestException(Constants.RESERVATION_CREATION_FAILED);
        }
    }


    private boolean isTableAvailable(Long tableId, LocalDateTime fechaReserva) {
        List<Reservations> reservas = reservationRepository
                .findByRestaurantTablesIdAndFechaReservaAndStatus(tableId, fechaReserva, Reservations.Status.ACTIVO);
        return reservas.isEmpty();
    }
}
