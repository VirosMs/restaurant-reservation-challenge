package com.virosms.restaurantreservationchallenge.repository;

import com.virosms.restaurantreservationchallenge.model.Tables.RestaurantTables;
import com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TablesRepository is an interface for managing RestaurantTables in the database.
 * It extends JpaRepository to provide basic CRUD operations and custom queries.
 */
@Repository
@EnableJpaRepositories
public interface TablesRepository extends JpaRepository<RestaurantTables, Long> {

    /**
     * Finds all restaurant tables and returns them as a list of TablesDTO.
     *
     * @return a list of TablesDTO containing table details
     */
    @Query("SELECT new com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO(t.nombre, t.capacidad, t.status) FROM RestaurantTables t")
    List<TablesDTO> findAllTablesAsDTO();

    /**
     * Finds a restaurant table by its name.
     *
     * @param nombre the name of the restaurant table
     * @return the RestaurantTables entity with the specified name
     */
    RestaurantTables findByNombre(String nombre);
}
