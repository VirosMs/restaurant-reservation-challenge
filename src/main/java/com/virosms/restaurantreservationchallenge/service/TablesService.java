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
import java.util.Optional;

/**
 * TablesService is a service class that handles operations related to restaurant tables.
 * It provides methods to create, update, delete, and retrieve tables.
 */
@Service
@Validated
@Transactional
public class TablesService {

    private final TablesRepository tablesRepository;

    private final TablesMapper tablesMapper;

    /**
     * Constructor for TablesService.
     *
     * @param tablesRepository the repository for managing RestaurantTables
     * @param tablesMapper     the mapper for converting between RestaurantTables and TablesDTO
     */
    @Autowired
    public TablesService(TablesRepository tablesRepository, TablesMapper tablesMapper) {
        this.tablesRepository = tablesRepository;
        this.tablesMapper = tablesMapper;
    }

    /**
     * Método para obtener todas las mesas.
     *
     * @return Respuesta con la lista de mesas.
     */
    public ResponseEntity<List<TablesDTO>> getAllTables() {

        List<TablesDTO> tables = tablesRepository.findAllTablesAsDTO();

        if (tables.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

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
            throw  new BadRequestException("Invalid table data");
        }

        if (tablesRepository.findByNombre(data.getNombre()) != null) {
            throw new AlreadyExistsException("Table with name " + data.getNombre() + " already exists");
        }

        try{
            RestaurantTables table = tablesMapper.toEntity(data);
            tablesRepository.save(table);
            return ResponseEntity.ok(new CreateTableResponse("Table created successfully", data));
        } catch (Exception e) {
            throw new BadRequestException("Error creating table: " + e.getMessage());
        }
    }

    /**
     * Métod para actualizar una mesa existente.
     *
     * @param id       ID de la mesa a actualizar.
     * @param newData  Nuevos datos de la mesa.
     * @return Respuesta sin contenido si la actualización es exitosa.
     */
    public ResponseEntity<Void> updateTable(Long id, @Valid TablesDTO newData) {

        if (Utils.isValidTable(newData)){
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

    /**
     * Métod para eliminar una mesa por su ID.
     *
     * @param id ID de la mesa a eliminar.
     * @return Respuesta sin contenido si la eliminación es exitosa.
     */
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

    /**
     * Métod para verificar si una mesa está disponible para una cantidad específica de personas.
     *
     * @param tableId         ID de la mesa a verificar.
     * @param cantidadPersonas Cantidad de personas para las que se verifica la disponibilidad.
     * @return true si la mesa tiene capacidad suficiente, false en caso contrario.
     */
    public boolean isAvailableCapacity(Long tableId, int cantidadPersonas) {

        return tablesRepository.findById(tableId).stream().anyMatch(tables ->
                tables.getCapacidad() >= cantidadPersonas);
    }

    /**
     * Métod para encontrar una mesa por su ID.
     *
     * @param tableId ID de la mesa a buscar.
     * @return Optional que contiene la mesa si se encuentra, o vacío si no se encuentra.
     */
    public Optional<RestaurantTables> findById(Long tableId) {
        return tablesRepository.findById(tableId);
    }
}
