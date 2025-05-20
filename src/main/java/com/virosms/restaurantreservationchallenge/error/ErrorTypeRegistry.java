package com.virosms.restaurantreservationchallenge.error;

public class ErrorTypeRegistry {

    public static String getErrorTypeUrl(String errorKey) {
        return "https://virosms.com/errors/" + errorKey.toLowerCase().replace("_", "-");
    }

    public static final String NOT_FOUND = "not-found";
    public static final String BAD_REQUEST = "bad-request";
    public static final String CONFLICT = "conflict";
    public static final String UNAUTHORIZED = "unauthorized";
    public static final String FORBIDDEN = "forbidden";
    public static final String INTERNAL_SERVER_ERROR = "internal-server-error";
}
