package com.example.jpa_empleos.controllers;

import com.example.jpa_empleos.models.EstatusVacante;
import com.example.jpa_empleos.models.Vacante;
import com.example.jpa_empleos.repository.VacantesRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jpa-vacante")
public class VacantesQueryController {

    private final VacantesRepository vacantesRepo;


    public VacantesQueryController(
            VacantesRepository vacantesRepo
    ) {

        this.vacantesRepo = vacantesRepo;
    }


    /*
     * =====================================================
     * 1. BUSCAR VACANTES POR ESTATUS
     * =====================================================
     *
     * Ejemplo:
     *
     * GET
     * http://localhost:8080/api/jpa-vacante/estatus?estatus=Aprobada
     */

    @GetMapping("/estatus")
    public List<Vacante> buscarPorEstatus(
            @RequestParam EstatusVacante estatus
    ) {

        return vacantesRepo.findByEstatus(
                estatus
        );
    }


    /*
     * =====================================================
     * 2. BUSCAR VACANTES DESTACADAS Y POR ESTATUS
     * =====================================================
     *
     * Ejemplo:
     *
     * GET
     * http://localhost:8080/api/jpa-vacante/destacadas?destacado=1&estatus=Aprobada
     */

    @GetMapping("/destacadas")
    public List<Vacante> buscarDestacadasYPorEstatus(
            @RequestParam int destacado,
            @RequestParam EstatusVacante estatus
    ) {

        return vacantesRepo
                .findByDestacadoAndEstatusOrderByIdDesc(
                        destacado,
                        estatus
                );
    }


    /*
     * =====================================================
     * 3. BUSCAR VACANTES POR RANGO DE SALARIO
     * =====================================================
     *
     * Ejemplo:
     *
     * GET
     * http://localhost:8080/api/jpa-vacante/salario?minimo=7000&maximo=14000
     */

    @GetMapping("/salario")
    public List<Vacante> buscarPorRangoSalario(
            @RequestParam Double minimo,
            @RequestParam Double maximo
    ) {

        return vacantesRepo
                .findBySalarioBetween(
                        minimo,
                        maximo
                );
    }


    /*
     * =====================================================
     * 4. BUSCAR POR RANGO DE SALARIO ORDENADO DESCENDENTE
     * =====================================================
     *
     * Ejemplo:
     *
     * GET
     * http://localhost:8080/api/jpa-vacante/salario/desc?minimo=7000&maximo=14000
     */

    @GetMapping("/salario/desc")
    public List<Vacante> buscarPorRangoSalarioDesc(
            @RequestParam Double minimo,
            @RequestParam Double maximo
    ) {

        return vacantesRepo
                .findBySalarioBetweenOrderBySalarioDesc(
                        minimo,
                        maximo
                );
    }


    /*
     * =====================================================
     * 5. BUSCAR VACANTES POR VARIOS ESTATUS
     * =====================================================
     *
     * Ejemplo:
     *
     * GET
     * http://localhost:8080/api/jpa-vacante/estatus-varios?estatus=Eliminada,Creada
     */

    @GetMapping("/estatus-varios")
    public List<Vacante> buscarPorVariosEstatus(
            @RequestParam List<EstatusVacante> estatus
    ) {

        EstatusVacante[] listaEstatus =
                estatus.toArray(
                        new EstatusVacante[0]
                );


        return vacantesRepo.findByEstatusIn(
                listaEstatus
        );
    }
}