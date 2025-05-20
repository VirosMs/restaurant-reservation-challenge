package com.virosms.restaurantreservationchallenge.model.User;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {

    CLIENT("client"), ADMIN("admin");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    @JsonValue
    public String getRole() {
        return role;
    }

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