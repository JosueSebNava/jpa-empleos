package com.example.jpa_empleos.repository;

import com.example.jpa_empleos.models.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacantesRepository extends JpaRepository<Vacante, Integer> {

}