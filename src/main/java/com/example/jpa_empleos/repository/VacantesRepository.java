package com.example.jpa_empleos.repository;

import com.example.jpa_empleos.models.Vacante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VacantesRepository
        extends JpaRepository<Vacante, Integer> {

    /*
     * Obtiene el ID más grande actualmente
     * registrado en la tabla Vacantes.
     */
    @Query("SELECT MAX(v.id) FROM Vacante v")
    Integer obtenerUltimoId();
}