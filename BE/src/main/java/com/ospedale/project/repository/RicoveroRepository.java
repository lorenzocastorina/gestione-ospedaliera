package com.ospedale.project.repository;

import com.ospedale.project.model.Ricovero;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RicoveroRepository extends JpaRepository<Ricovero, Long> {

    @Query("SELECT r FROM Ricovero r WHERE r.id = :id AND r.inizio_ricovero IS NOT NULL AND r.fine_ricovero IS NULL")
    Optional<Ricovero> findRicoveroNonTerminato (@Param("id") Long id);

    @Query("SELECT r FROM Ricovero r WHERE r.letto.id = :id AND r.inizio_ricovero IS NOT NULL AND r.fine_ricovero IS NULL")
    Optional<Ricovero> findRicoveroNonTerminatoByIdLetto (@Param("id") Long id);

    Optional<Ricovero> findRicoveroByPaziente_Id (Long id);

    @Query("SELECT r FROM Ricovero r WHERE r.paziente.id = :id AND  r.fine_ricovero is null")
    Optional<Ricovero> findUltimoRicoveroAttivoByPazienteId (Long id);

    @Query("SELECT r FROM Ricovero r WHERE r.paziente.id = :id AND r.fine_ricovero is not null ORDER BY r.fine_ricovero desc")
    List<Ricovero> findUltimoRicoveroNonAttivoByPazienteId(Long id, Pageable pageable);

}
