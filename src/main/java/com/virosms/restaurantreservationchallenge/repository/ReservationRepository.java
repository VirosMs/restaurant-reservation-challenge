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


@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Long> {


    List<Reservations> findByRestaurantTablesIdAndFechaReservaAndStatus(Long tableId, LocalDateTime fechaReserva, Reservations.Status status);


    @Query("SELECT r FROM Reservations r JOIN FETCH r.user JOIN FETCH r.restaurantTables WHERE r.id = :id")
    Optional<Reservations> findByIdWithUserAndTable(@Param("id") Long id); // Changed return to Optional for safety


    @Override
    @EntityGraph(attributePaths = {"restaurantTables"})
    Optional<Reservations> findById(Long id);


    @Query("SELECT r FROM Reservations r JOIN FETCH r.user JOIN FETCH r.restaurantTables")
    List<Reservations> findAllWithUserAndTable();
}