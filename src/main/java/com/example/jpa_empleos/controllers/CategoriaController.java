package com.example.jpa_empleos.controllers;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.repository.CategoriasRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriasRepository categoriasRepository;

    public CategoriaController(CategoriasRepository categoriasRepository) {
        this.categoriasRepository = categoriasRepository;
    }

    @GetMapping
    public List<Categoria> listarTodas() {
        return (List<Categoria>) categoriasRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Integer id) {
        Optional<Categoria> categoria = categoriasRepository.findById(id);
        return categoria.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria) {
        Categoria guardada = categoriasRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Integer id, @RequestBody Categoria categoria) {
        if (!categoriasRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoria.setId(id);
        Categoria actualizada = categoriasRepository.save(categoria);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!categoriasRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriasRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
