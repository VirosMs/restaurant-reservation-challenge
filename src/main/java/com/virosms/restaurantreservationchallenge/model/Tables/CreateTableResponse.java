package com.virosms.restaurantreservationchallenge.model.Tables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response class for creating a table, containing a message and the created table details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTableResponse {
    private String message;
    private TablesDTO table;

}
