package com.virosms.restaurantreservationchallenge.repository;

import com.virosms.restaurantreservationchallenge.model.reservation.Reservations;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * ReservationRepository is an interface for managing Reservations in the database.
 * It extends JpaRepository to provide basic CRUD operations and custom queries.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Long> {


    /**
     * Finds all active reservations for a specific table within a given time range.
     *
     * @param tableId the ID of the restaurant table
     * @param inicio  the start time of the range
     * @param fin     the end time of the range
     * @return a list of active Reservations for the specified table and time range
     */
    @Query("SELECT r FROM Reservations r WHERE r.restaurantTables.id = :tableId AND " +
            "(:inicio < r.fechaReservaFin AND :fin > r.fechaReservaInicio) AND r.status = com.virosms.restaurantreservationchallenge.model.reservation.Reservations.Status.ACTIVO")
    List<Reservations> findReservasActivasEnRango(@Param("tableId") Long tableId,
                                                  @Param("inicio") LocalDateTime inicio,
                                                  @Param("fin") LocalDateTime fin);


    /**
     * Finds all reservations for a specific table.
     *
     * @param id the restaurant table
     * @return a list of Reservations for the specified table
     */
    @Query("SELECT r FROM Reservations r JOIN FETCH r.user JOIN FETCH r.restaurantTables WHERE r.id = :id")
    Optional<Reservations> findByIdWithUserAndTable(@Param("id") Long id); // Changed return to Optional for safety

    /**
     * Finds all reservations for a specific table.
     *
     * @param id the restaurant table
     * @return a list of Reservations for the specified table
     */
    @Override
    @EntityGraph(attributePaths = {"restaurantTables"})
    Optional<Reservations> findById(Long id);


    /**
     * Finds all reservations with their associated user and restaurant tables.
     *
     * @return a list of Reservations with user and table information
     */
    @Query("SELECT r FROM Reservations r JOIN FETCH r.user JOIN FETCH r.restaurantTables")
    List<Reservations> findAllWithUserAndTable();
}