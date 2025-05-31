package com.virosms.restaurantreservationchallenge.controller;


import com.virosms.restaurantreservationchallenge.model.reservation.ReservationsRequest;
import com.virosms.restaurantreservationchallenge.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
    public ResponseEntity<?> createReservation(@RequestBody @Valid ReservationsRequest reservationsRequest) {
        return reservationService.createReservation(reservationsRequest);
    }
}
