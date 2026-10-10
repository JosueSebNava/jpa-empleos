package com.example.jpa_empleos.controllers;

import com.example.jpa_empleos.models.Perfil;
import com.example.jpa_empleos.models.Usuario;

import com.example.jpa_empleos.repository.PerfilesRepository;
import com.example.jpa_empleos.repository.UsuarioRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jpa-usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;
    private final PerfilesRepository perfilesRepo;


    public UsuarioController(
            UsuarioRepository usuarioRepo,
            PerfilesRepository perfilesRepo
    ) {

        this.usuarioRepo = usuarioRepo;
        this.perfilesRepo = perfilesRepo;
    }


    /*
     * =====================================================
     * BUSCAR USUARIO POR ID
     * =====================================================
     *
     * GET
     * http://localhost:8080/api/jpa-usuario/1
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuario(
            @PathVariable Integer id
    ) {

        Optional<Usuario> usuarioOptional =
                usuarioRepo.findById(id);


        if (usuarioOptional.isPresent()) {

            return ResponseEntity.ok(
                    usuarioOptional.get()
            );
        }


        return ResponseEntity
                .notFound()
                .build();
    }


    /*
     * =====================================================
     * CREAR USUARIO CON PERFILES
     * =====================================================
     *
     * POST
     * http://localhost:8080/api/jpa-usuario
     */

    @PostMapping
    public ResponseEntity<?> crearUsuario(
            @RequestBody Usuario usuario
    ) {

        /*
         * Validar perfiles recibidos.
         */
        if (
                usuario.getPerfiles() != null
                        &&
                        !usuario.getPerfiles().isEmpty()
        ) {

            for (Perfil perfil : usuario.getPerfiles()) {

                if (
                        perfil.getId() == null
                                ||
                                !perfilesRepo.existsById(
                                        perfil.getId()
                                )
                ) {

                    return ResponseEntity
                            .badRequest()
                            .body(
                                    "El perfil con ID "
                                            + perfil.getId()
                                            + " no existe."
                            );
                }
            }
        }


        Usuario usuarioGuardado =
                usuarioRepo.save(usuario);


        return ResponseEntity.ok(
                usuarioGuardado
        );
    }


    /*
     * =====================================================
     * CREAR PERFILES
     * =====================================================
     *
     * POST
     * http://localhost:8080/api/jpa-usuario/perfiles
     */

    @PostMapping("/perfiles")
    public ResponseEntity<List<Perfil>> crearPerfiles(
            @RequestBody List<Perfil> perfiles
    ) {

        List<Perfil> perfilesGuardados =
                perfilesRepo.saveAll(perfiles);


        return ResponseEntity.ok(
                perfilesGuardados
        );
    }
}