package com.example.jpa_empleos.controllers;

import com.example.jpa_empleos.models.Perfil;
import com.example.jpa_empleos.models.Usuario;

import com.example.jpa_empleos.repository.PerfilesRepository;
import com.example.jpa_empleos.repository.UsuarioRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
         * Crear un conjunto donde se colocarán
         * los perfiles existentes en la BD.
         */
        Set<Perfil> perfilesEncontrados =
                new HashSet<>();


        /*
         * Revisar los perfiles enviados
         * desde el JSON.
         */
        if (
                usuario.getPerfiles() != null
                        &&
                        !usuario.getPerfiles().isEmpty()
        ) {

            for (
                    Perfil perfilRecibido :
                    usuario.getPerfiles()
            ) {

                /*
                 * Comprobar que venga un ID.
                 */
                if (perfilRecibido.getId() == null) {

                    return ResponseEntity
                            .badRequest()
                            .body(
                                    "Todos los perfiles deben tener un ID."
                            );
                }


                /*
                 * Buscar el perfil real
                 * en la base de datos.
                 */
                Optional<Perfil> perfilOptional =
                        perfilesRepo.findById(
                                perfilRecibido.getId()
                        );


                /*
                 * Si no existe, regresar error.
                 */
                if (perfilOptional.isEmpty()) {

                    return ResponseEntity
                            .badRequest()
                            .body(
                                    "El perfil con ID "
                                            + perfilRecibido.getId()
                                            + " no existe."
                            );
                }


                /*
                 * Agregar el perfil real
                 * recuperado desde MySQL.
                 */
                perfilesEncontrados.add(
                        perfilOptional.get()
                );
            }
        }


        /*
         * Asignar al usuario los perfiles
         * recuperados desde la BD.
         */
        usuario.setPerfiles(
                perfilesEncontrados
        );


        /*
         * Guardar usuario.
         *
         * JPA insertará el Usuario y
         * las relaciones en UsuarioPerfil.
         */
        Usuario usuarioGuardado =
                usuarioRepo.save(
                        usuario
                );


        return ResponseEntity.ok(
                usuarioGuardado
        );
    }
}