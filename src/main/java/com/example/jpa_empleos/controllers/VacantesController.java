package com.example.jpa_empleos.controllers;

import com.example.jpa_empleos.models.Vacante;
import com.example.jpa_empleos.repository.VacantesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacantes")
public class VacantesController {

    @Autowired
    private VacantesRepository vacantesRepo;

    /*
     * ======================================================
     * BUSCAR TODAS LAS VACANTES
     * ======================================================
     */

    @GetMapping
    public List<Vacante> buscarVacantes() {

        return vacantesRepo.findAll();
    }


    /*
     * ======================================================
     * GUARDAR UNA VACANTE
     * ======================================================
     */

    @PostMapping
    public Vacante guardarVacante(
            @RequestBody Vacante vacante
    ) {

        return vacantesRepo.save(vacante);
    }
}