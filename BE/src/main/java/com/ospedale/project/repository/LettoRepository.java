package com.ospedale.project.repository;

import com.ospedale.project.model.Letto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LettoRepository extends JpaRepository<Letto, Long> {
    Optional<Letto> findFirstByStatoFalse();
    Optional<Letto> findFirstByStatoTrue();
    List<Letto> findAllByStatoIsFalse();

    @Query("SELECT l FROM Letto l JOIN Ricovero r ON l.id=r.letto.id WHERE l.stato = true AND r.fine_ricovero is null")
    Optional<Letto> findFirstOccupatoConRicoveroAssociatoInCorso();
}