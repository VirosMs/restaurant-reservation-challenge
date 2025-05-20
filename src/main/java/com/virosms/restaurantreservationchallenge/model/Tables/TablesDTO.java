package com.virosms.restaurantreservationchallenge.model.Tables;

public record TablesDTO(
        String nombre,
        Integer capacidad,
        Tables.Status status
) {
}
