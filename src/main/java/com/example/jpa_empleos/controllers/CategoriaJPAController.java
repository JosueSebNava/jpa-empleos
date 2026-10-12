package com.example.jpa_empleos.controllers;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.repository.CategoriasJPARepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jpa-categorias")
public class CategoriaJPAController {

    @Autowired
    private CategoriasJPARepository categoriasJPARepo;

    /**
     * 1. Obtener todas las categorías
     * GET http://localhost:8080/api/jpa-categorias
     */
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodas() {

        List<Categoria> categorias =
                categoriasJPARepo.findAll();

        return ResponseEntity.ok(categorias);
    }

    /**
     * 2. Obtener categorías ordenadas por nombre
     * GET http://localhost:8080/api/jpa-categorias/ordenadas
     */
    @GetMapping("/ordenadas")
    public ResponseEntity<List<Categoria>> obtenerOrdenadas() {

        List<Categoria> categorias =
                categoriasJPARepo.findAll(
                        Sort.by("nombre").descending()
                );

        return ResponseEntity.ok(categorias);
    }

    /**
     * 3. Obtener categorías con paginación
     * GET http://localhost:8080/api/jpa-categorias/paginadas?pagina=0&cantidad=5
     */
    @GetMapping("/paginadas")
    public ResponseEntity<Page<Categoria>> obtenerPaginadas(

            @RequestParam(defaultValue = "0") int pagina,

            @RequestParam(defaultValue = "5") int cantidad) {

        Page<Categoria> categorias =
                categoriasJPARepo.findAll(
                        PageRequest.of(
                                pagina,
                                cantidad
                        )
                );

        return ResponseEntity.ok(categorias);
    }

    /**
     * 4. Obtener categorías paginadas y ordenadas
     * GET http://localhost:8080/api/jpa-categorias/paginadas/ordenadas?pagina=0&cantidad=5
     */
    @GetMapping("/paginadas/ordenadas")
    public ResponseEntity<Page<Categoria>>
    obtenerPaginadasOrdenadas(

            @RequestParam(defaultValue = "0") int pagina,

            @RequestParam(defaultValue = "5") int cantidad) {

        Page<Categoria> categorias =
                categoriasJPARepo.findAll(
                        PageRequest.of(
                                pagina,
                                cantidad,
                                Sort.by("nombre").descending()
                        )
                );

        return ResponseEntity.ok(categorias);
    }

    /**
     * 5. Eliminar todas las categorías
     * DELETE http://localhost:8080/api/jpa-categorias/todas
     */
    @DeleteMapping("/todas")
    public ResponseEntity<Map<String, String>> eliminarTodas() {

        categoriasJPARepo.deleteAllInBatch();

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Todas las categorías fueron eliminadas correctamente"
                )
        );
    }
}