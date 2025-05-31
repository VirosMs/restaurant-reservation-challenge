package com.virosms.restaurantreservationchallenge.model.User;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum representing user roles in the system.
 * This enum defines two roles: CLIENT and ADMIN.
 * It provides methods to get the role as a string and to create a UserRole from a string value.
 */
public enum UserRole {

    CLIENT("client"), ADMIN("admin");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    /**
     * Returns the string representation of the user role.
     *
     * @return the role as a string
     */
    @JsonValue
    public String getRole() {
        return role;
    }

    /**
     * Creates a UserRole from a string value.
     * If the value does not match any of the defined roles, an IllegalArgumentException is thrown.
     *
     * @param value the string representation of the role
     * @return the corresponding UserRole
     * @throws IllegalArgumentException if the value does not match any role
     */
    @JsonCreator
    public static UserRole fromValue(String value) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.role.equalsIgnoreCase(value)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }
}