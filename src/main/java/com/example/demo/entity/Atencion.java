package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "atencion")
@Data
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombrePaciente;

    @Column(nullable = false)
    private String apellidoPaciente;

    @Column(nullable = false)
    private String nombreMedico;

    @Column(nullable = false)
    private String apellidoMedico;

    @Column(nullable = false)
    private LocalDate fechaAtencion;

    private String referencia;
}
