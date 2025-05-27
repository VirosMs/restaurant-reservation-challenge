package com.virosms.restaurantreservationchallenge.utils;


import com.virosms.restaurantreservationchallenge.model.Tables.RestaurantTables;
import com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO;
import com.virosms.restaurantreservationchallenge.model.User.RegisterDTO;
import com.virosms.restaurantreservationchallenge.model.User.Users;
import com.virosms.restaurantreservationchallenge.model.reservation.ReservationsRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utils {


    /**
     * Validates the user data.
     *
     * @param user the user object to validate
     * @return true if the user data is valid, false otherwise
     */
    public static boolean validateUser(Object user) {
        if (user instanceof Users userObj) {
            return isValidUser(userObj.getNombre(), userObj.getEmail(), userObj.getPassword());
        }
        if (user instanceof RegisterDTO(String nombre, String email, String password)) {
            return isValidUser(nombre, email, password);
        }
        return false;
    }

    /**
     * Validates the user data.
     *
     * @param nombre   the name of the user
     * @param email    the email of the user
     * @param password the password of the user
     * @return true if the user data is valid, false otherwise
     */
    private static boolean isValidUser(String nombre, String email, String password) {
        return nombre != null && !nombre.isEmpty() &&
                isValidEmail(email) &&
                password != null && !password.isEmpty();
    }

    /**
     * Validates the email format.
     *
     * @param email the email to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && !email.isBlank() && email.matches(Constants.EMAIL_REGEX);
    }

    /**
     * Validates the table data.
     *
     * @param data the table data to validate
     * @return true if the table data is valid, false otherwise
     */
    public static boolean isValidTable(TablesDTO data) {
        return data.nombre() == null || data.nombre().isBlank() ||
                data.capacidad() <= 0 ||
                data.status() == null || !RestaurantTables.Status.validateStatus(data.status());
    }

    public static boolean validateRequest(ReservationsRequest request) {
        System.out.println("Validating request: " + request);
        return request != null &&
                request.user() != null && request.user().getId() != null &&
                request.table() != null && request.table().getId() != null &&
                validateDate(request.fechaReserva()) && validateCapacityPersons(request.cantidadPersonas());
    }

    public static boolean validateCapacityPersons(Integer cantidadPersonas) {
        return cantidadPersonas != null && cantidadPersonas > 0;
    }


    public static boolean validateDate(LocalDateTime date) {
        return date != null && date.isBefore(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
}
