package com.virosms.restaurantreservationchallenge.controller;

import com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO;
import com.virosms.restaurantreservationchallenge.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("/tables")
public class TablesController {

    @Autowired
    private TablesService tablesService;

    /**
     * Endpoint para obtener la vista de las mesas.
     *
     * @return Nombre de la vista de las mesas.
     */
    @GetMapping
    public ResponseEntity<List<TablesDTO>> getTables() {
        return tablesService.getAllTables();
    }
}
