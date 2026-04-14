package com.example.demo.repository;

import com.example.demo.entity.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AtencionRepository extends JpaRepository<Atencion, Integer> {

    List<Atencion> findByNombrePacienteAndApellidoPacienteOrderByFechaAtencionDesc(
            String nombrePaciente,
            String apellidoPaciente
    );

    List<Atencion> findByFechaAtencionBetween(
            java.time.LocalDate fechaInicio,
            java.time.LocalDate fechaFin
    );
}