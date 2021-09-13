package com.ospedale.project.repository;

import com.ospedale.project.model.Operatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperatoreRepository extends JpaRepository<Operatore, Long> {
    Optional<Operatore> findByUsernameAndPassword(String username, String password);
}
