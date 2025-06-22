package com.virosms.restaurantreservationchallenge.controller;


import com.virosms.restaurantreservationchallenge.model.reservation.ReservationsRequest;
import com.virosms.restaurantreservationchallenge.model.reservation.ReservationsResponse;
import com.virosms.restaurantreservationchallenge.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    /**
     * Handles the creation of a new reservation.
     *
     * @param reservationsRequest the request body containing reservation details
     * @return a redirect to the reservation page
     */

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody @Valid ReservationsRequest reservationsRequest, @Valid HttpServletRequest servletRequest) {
        return reservationService.createReservation(reservationsRequest, servletRequest);
    }


    /**
     * Retrieves a list of reservations from a user logged.
     *
     * @param request the HTTP request
     * @return a list of reservations
     */
    @GetMapping
    public ResponseEntity<List<ReservationsResponse>> getReservations(@Valid HttpServletRequest request) {
        return ResponseEntity.ok(reservationService.getReservations(request));
    }


    @PatchMapping("/{reservationId}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable("reservationId") Long reservationId, @Valid HttpServletRequest request) {
        return reservationService.cancelReservation(reservationId, request);
    }

}
