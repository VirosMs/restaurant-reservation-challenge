package com.virosms.restaurantreservationchallenge.model.Tables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTableResponse {
    private String message;
    private TablesDTO table;

}
