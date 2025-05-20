package com.virosms.restaurantreservationchallenge.controller;

import com.virosms.restaurantreservationchallenge.model.Tables.CreateTableResponse;
import com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO;
import com.virosms.restaurantreservationchallenge.service.TablesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * Endpoint para crear una nueva mesa.
     *
     * @param data Datos de la mesa a crear.
     * @return Respuesta con el mensaje de éxito y los datos de la mesa creada.
     */
    @PostMapping
    public ResponseEntity<CreateTableResponse> createTable(@RequestBody @Valid TablesDTO data) {
        return tablesService.createTable(data);
    }
}
