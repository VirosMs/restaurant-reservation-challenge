package com.virosms.restaurantreservationchallenge.utils;


import com.virosms.restaurantreservationchallenge.model.User.RegisterDTO;
import com.virosms.restaurantreservationchallenge.model.User.Users;

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
        if (user instanceof RegisterDTO registerDTO) {
            return isValidUser(registerDTO.nombre(), registerDTO.email(), registerDTO.password());
        }
        return false;
    }

    /**
     * Validates the user data.
     *
     * @param nombre  the name of the user
     * @param email   the email of the user
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
}
