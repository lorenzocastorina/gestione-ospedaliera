package com.ospedale.project.repository;

import com.ospedale.project.model.Paziente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PazienteRepository extends JpaRepository<Paziente, Long> {

    List<Paziente> findAllByIsActiveIsTrue();

    Optional<Paziente> findByCfAndIsActiveIsTrue(String cf);

    Optional<Paziente> findByCfAndIsActiveIsFalse(String cf);

    Optional<Paziente> findByIdAndIsActiveIsTrue(Long id);

    @Query("SELECT DISTINCT p FROM  Paziente p WHERE p.isActive=true AND p.stato = 'ProntoSoccorso'")
    List<Paziente> findAllProntoSoccorso();

    @Query("SELECT DISTINCT p FROM Paziente p WHERE p.isActive=true AND p.stato = 'Ricoverato'")
    List<Paziente> findAllRicoverati();

    @Query("SELECT DISTINCT p FROM Paziente p WHERE p.isActive=true AND p.stato = 'Dimesso'")
    List<Paziente> findAllDimessi();

    @Query("SELECT DISTINCT p FROM Paziente p WHERE p.isActive=false")
    List<Paziente> findAllEliminati();

}

