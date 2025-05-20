package com.virosms.restaurantreservationchallenge.repository;

import com.virosms.restaurantreservationchallenge.model.Tables.Tables;
import com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface TablesRepository extends JpaRepository<Tables, Long> {

    @Query("SELECT new com.virosms.restaurantreservationchallenge.model.Tables.TablesDTO(t.nombre, t.capacidad, t.status) FROM tables t")
    List<TablesDTO> findAllTablesAsDTO();

    Tables findByNombre(String nombre);
}
