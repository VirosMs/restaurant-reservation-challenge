package com.virosms.restaurantreservationchallenge.model.Tables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for restaurant tables.
 * This class is used to transfer table data between different layers of the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TablesDTO {
    String nombre;
    Integer capacidad;
    RestaurantTables.Status status;
}