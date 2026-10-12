package com.example.jpa_empleos.repository;

import com.example.jpa_empleos.models.EstatusVacante;
import com.example.jpa_empleos.models.Vacante;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacantesRepository
        extends JpaRepository<Vacante, Integer> {

    List<Vacante> findByEstatus(
            EstatusVacante estatus
    );
    List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(int destacado,
                                                         EstatusVacante estatus);

    List<Vacante> findBySalarioBetween(Double salarioInicial, Double salarioFinal);
    List<Vacante> findBySalarioBetweenOrderBySalarioDesc(Double salarioInicial, Double salarioFinal);
}