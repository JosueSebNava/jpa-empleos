package com.example.jpa_empleos.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "Vacantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vacante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "descripcion", nullable = false, columnDefinition = "text")
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "salario", nullable = false)
    private Double salario;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", nullable = false)
    private EstatusVacante estatus;

    @Column(name = "destacado", nullable = false)
    private Integer destacado;

    @Column(name = "imagen", nullable = false, length = 250)
    private String imagen;

    @Column(name = "detalles", columnDefinition = "text")
    private String detalles;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;
}