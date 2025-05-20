package com.virosms.restaurantreservationchallenge.service;

import com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO;
import com.virosms.restaurantreservationchallenge.repository.TablesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@Transactional
public class TablesService {

    @Autowired
    private TablesRepository tablesRepository;

    /**
     * Método para obtener todas las mesas.
     *
     * @return Lista de mesas.
     */
    public ResponseEntity<List<TablesDTO>> getAllTables() {

        List<TablesDTO> tables = tablesRepository.findAllTablesAsDTO();

        System.out.println(tablesRepository.findAll());

        if (tables.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        System.out.println("Tables " + tables);

        return ResponseEntity.ok().body(tables);
    }

}
