package com.virosms.restaurantreservationchallenge.service;

import com.virosms.restaurantreservationchallenge.infra.exception.AlreadyExistsException;
import com.virosms.restaurantreservationchallenge.infra.exception.BadRequestException;
import com.virosms.restaurantreservationchallenge.infra.exception.NotExistException;
import com.virosms.restaurantreservationchallenge.mapper.TablesMapper;
import com.virosms.restaurantreservationchallenge.model.Tables.CreateTableResponse;
import com.virosms.restaurantreservationchallenge.model.Tables.RestaurantTables;
import com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO;
import com.virosms.restaurantreservationchallenge.repository.TablesRepository;
import com.virosms.restaurantreservationchallenge.utils.Utils;
import jakarta.validation.Valid;
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

    private final TablesRepository tablesRepository;

    private final TablesMapper tablesMapper;

    @Autowired
    public TablesService(TablesRepository tablesRepository, TablesMapper tablesMapper) {
        this.tablesRepository = tablesRepository;
        this.tablesMapper = tablesMapper;
    }

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


    /**
     * Método para crear una nueva mesa.
     *
     * @param data Datos de la mesa a crear.
     * @return Respuesta con el mensaje de éxito y los datos de la mesa creada.
     */
    public ResponseEntity<CreateTableResponse> createTable(TablesDTO data) {

        if (Utils.isValidTable(data)){
            System.out.println("Invalid table " + data);
            throw  new BadRequestException("Invalid table data");
        }

        if (tablesRepository.findByNombre(data.nombre()) != null) {
            throw new AlreadyExistsException("Table with name " + data.nombre() + " already exists");
        }

        try{
            RestaurantTables table = tablesMapper.toEntity(data);
            tablesRepository.save(table);
            return ResponseEntity.ok(new CreateTableResponse("Table created successfully", data));
        } catch (Exception e) {
            throw new BadRequestException("Error creating table: " + e.getMessage());
        }
    }

    public ResponseEntity<Void> updateTable(Long id, @Valid TablesDTO newData) {

        if (Utils.isValidTable(newData)){
            System.out.println("Invalid table " + newData);
            throw  new BadRequestException("Invalid table data");
        }

        RestaurantTables oldData = tablesRepository.findById(id)
                .orElseThrow(() -> new NotExistException("Table with id " + id + " not exists"));

        if (oldData == null) {
            throw new BadRequestException("Table with id " + id + " not found");
        }

        tablesMapper.updateEntityFromDto(newData, oldData);

        try {
            tablesRepository.save(oldData);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new BadRequestException("Error updating table: " + e.getMessage());
        }
    }

    public ResponseEntity<Void> deleteTable(Long id) {

        if (!tablesRepository.existsById(id)) {
            throw new NotExistException("Table with id " + id + " not exists");
        }

        try {
            tablesRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new BadRequestException("Error deleting table: " + e.getMessage());
        }
    }

    public boolean isAvailableCapacity(Long tableId, int cantidadPersonas) {

        return tablesRepository.findById(tableId).stream().anyMatch(tables ->
                tables.getCapacidad() >= cantidadPersonas);
    }
}
