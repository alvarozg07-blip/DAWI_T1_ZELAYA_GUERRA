package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AtencionDTO {

    private Integer id;

    private String nombrePaciente;
    private String apellidoPaciente;

    private String nombreMedico;
    private String apellidoMedico;

    private LocalDate fechaAtencion;

    private String referencia;
}