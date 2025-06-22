package com.virosms.restaurantreservationchallenge.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.virosms.restaurantreservationchallenge.infra.exception.InactiveTableException;
import com.virosms.restaurantreservationchallenge.infra.exception.InvalidValueRequestException;
import com.virosms.restaurantreservationchallenge.infra.exception.NotFoundTableException;
import com.virosms.restaurantreservationchallenge.infra.exception.TableNotAvailableException;
import com.virosms.restaurantreservationchallenge.infra.security.JwtUtils;
import com.virosms.restaurantreservationchallenge.mapper.ReservationsMapper;
import com.virosms.restaurantreservationchallenge.model.Tables.RestaurantTables;
import com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO;
import com.virosms.restaurantreservationchallenge.model.User.UserDTO;
import com.virosms.restaurantreservationchallenge.model.User.Users;
import com.virosms.restaurantreservationchallenge.model.reservation.Reservations;
import com.virosms.restaurantreservationchallenge.model.reservation.ReservationsRequest;
import com.virosms.restaurantreservationchallenge.model.reservation.ReservationsResponse;
import com.virosms.restaurantreservationchallenge.repository.ReservationRepository;
import com.virosms.restaurantreservationchallenge.repository.UsersRepository;
import com.virosms.restaurantreservationchallenge.utils.Constants;
import com.virosms.restaurantreservationchallenge.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/* * ReservationService is a service class that handles reservation-related operations.
 * It provides methods to create reservations, check table availability, and manage reservation data.
 */
@Service
@Validated
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UsersRepository usersRepository;
    private final TablesService tablesService;
    private final ReservationsMapper reservationMapper;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(ReservationService.class);

    /**
     * Constructor for ReservationService.
     *
     * @param reservationRepository the repository for managing Reservations
     * @param tablesService         the service for managing RestaurantTables
     * @param reservationMapper     the mapper for converting between Reservations and ReservationsResponse
     */
    @Autowired
    public ReservationService(ReservationRepository reservationRepository, TablesService tablesService, @Qualifier("reservationsMapperImpl") ReservationsMapper reservationMapper, JwtUtils jwtUtils, UsersRepository usersRepository, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.tablesService = tablesService;
        this.reservationMapper = reservationMapper;
        this.jwtUtils = jwtUtils;
        this.usersRepository = usersRepository;
        this.userService = userService;
    }

    /**
     * Checks if the reservation response is valid and fills in missing data from the fallback table.
     *
     * @param response      the ReservationsResponse to check
     * @param fallbackTable the fallback table to use for missing data
     * @return an Optional containing the checked ReservationsResponse, or empty if the response is null
     */
    private static Optional<ReservationsResponse> checkReservationResponse(ReservationsResponse response, RestaurantTables fallbackTable) {
        if (response == null) {
            return Optional.empty();
        }
        TablesDTO mesa = response.getTable();
        if (mesa == null ||
                mesa.getNombre() == null ||
                mesa.getCapacidad() == null ||
                mesa.getStatus() == null) {
            // Recuperar datos faltantes de fallbackTable si es necesario
            if (mesa == null) {
                TablesDTO fallbackTableDto = new TablesDTO(
                        fallbackTable.getNombre(),
                        fallbackTable.getCapacidad(),
                        fallbackTable.getStatus()
                );
                response.setTable(fallbackTableDto);
            } else {
                if (mesa.getNombre() == null) mesa.setNombre(fallbackTable.getNombre());
                if (mesa.getCapacidad() == null) mesa.setCapacidad(fallbackTable.getCapacidad());
                if (mesa.getStatus() == null) mesa.setStatus(fallbackTable.getStatus());
            }
        }
        return Optional.of(response);
    }

    /**
     * Creates a new reservation based on the provided ReservationsRequest.
     *
     * @param reservationsRequest the request containing reservation details
     * @return a ResponseEntity containing the created ReservationsResponse
     * @throws InvalidValueRequestException if the request is invalid or if the reservation cannot be created
     * @throws NotFoundTableException       if the specified table does not exist
     * @throws InactiveTableException       if the specified table is inactive
     * @throws TableNotAvailableException   if the specified table is not available for reservation
     */
    public ResponseEntity<?> createReservation(ReservationsRequest reservationsRequest, HttpServletRequest request) {

        UserDTO userDTO = userService.getUserDTOFromRequest(request);

        if(!Utils.validateUser(userDTO)) {
            throw new InvalidValueRequestException(Constants.INVALID_USER_DATA);
        }

        if (!Utils.validateRequest(reservationsRequest)) {
            throw new InvalidValueRequestException(Constants.INVALID_RESERVATION_REQUEST);
        }

        RestaurantTables mesa = tablesService.findById(reservationsRequest.table().getId())
                .orElseThrow(() -> new NotFoundTableException("No se encontró la mesa con ID: " + reservationsRequest.table().getId() + "." +
                        " Por favor, verifica el ID de la mesa e inténtalo de nuevo."));

        if (mesa.getStatus() == RestaurantTables.Status.INACTIVA) {
            throw new InactiveTableException("La mesa " + mesa.getId() + " está inactiva y no puede ser reservada.");
        }

        if (!isTableAvailable(reservationsRequest.table().getId(), reservationsRequest.fechaReservaInicio())) {
            throw new TableNotAvailableException(Constants.TABLE_NOT_AVAILABLE);
        }

        if (!tablesService.isAvailableCapacity(reservationsRequest.table().getId(), reservationsRequest.cantidadPersonas())) {
            throw new InvalidValueRequestException(Constants.TABLE_CAPACITY_EXCEEDED);
        }


        try {
            Reservations reservation = reservationMapper.mapToEntityActive(reservationsRequest, userDTO);

            reservation.getRestaurantTables().setStatus(RestaurantTables.Status.RESERVADA);

            Reservations savedReservation = reservationRepository.save(reservation);

            Optional<Reservations> fullReservation = reservationRepository.findByIdWithUserAndTable(savedReservation.getId());

            ReservationsResponse response = fullReservation
                    .map(reservationMapper::mapToResponse)
                    .orElseThrow(() -> new InvalidValueRequestException(Constants.RESERVATION_CREATION_FAILED));

            checkReservationResponse(response, mesa)
                    .ifPresent(res -> {
                        res.getTable().setStatus(RestaurantTables.Status.RESERVADA);
                        res.getTable().setCapacidad(mesa.getCapacidad());
                        res.getTable().setNombre(mesa.getNombre());
                    });

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new InvalidValueRequestException(Constants.RESERVATION_CREATION_FAILED);
        }
    }

    /**
     * Checks if a table is available for reservation at the specified time.
     *
     * @param tableId      the ID of the restaurant table
     * @param fechaReserva the date and time of the reservation
     * @return true if the table is available, false otherwise
     */
    private boolean isTableAvailable(Long tableId, LocalDateTime fechaReserva) {
        List<Reservations> reservasExistentes = reservationRepository.findReservasActivasEnRango(tableId, fechaReserva, fechaReserva.plusHours(Constants.RESERVATION_DURATION_HOURS));
        return reservasExistentes.isEmpty();
    }

    /**
     * Retrieves all reservations for the user associated with the provided HTTP request.
     *
     * @param request the HTTP request containing the user's JWT token
     * @return a list of Reservations for the user
     * @throws InvalidValueRequestException if the reservation has an invalid or inactive table
     */
    public List<ReservationsResponse> getReservations(HttpServletRequest request) {

        List<Reservations> reservations = reservationRepository.findByUserId(userService.getUserId(request));

        if (reservations.isEmpty()) {
            return List.of();
        }

        return  reservations.stream()
                .map(reservationMapper::mapToResponse)
                .toList();
    }


}
