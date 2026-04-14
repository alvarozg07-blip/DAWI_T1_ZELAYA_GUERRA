package com.example.demo.controller;

import com.example.demo.dto.AtencionDTO;
import com.example.demo.entity.Atencion;
import com.example.demo.repository.AtencionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/atenciones")
public class AtencionController {

    private final AtencionRepository repository;

    public AtencionController(AtencionRepository repository) {
        this.repository = repository;
    }

    // LISTAR TODAS
    @GetMapping
    public ResponseEntity<List<AtencionDTO>> listar() {
        List<AtencionDTO> lista = repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // CREARR
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody AtencionDTO dto) {

        if (dto.getNombrePaciente() == null ||
            dto.getApellidoPaciente() == null ||
            dto.getNombreMedico() == null ||
            dto.getApellidoMedico() == null ||
            dto.getFechaAtencion() == null) {
            return ResponseEntity.badRequest().build();
        }

        Atencion atencion = toEntity(dto);
        repository.save(atencion);

        return ResponseEntity.status(201).build();
    }

    // Historial

    @GetMapping("/historial")
    public ResponseEntity<?> historial(
            @RequestParam String nombrePaciente,
            @RequestParam String apellidoPaciente) {

        List<Atencion> lista = repository
                .findByNombrePacienteAndApellidoPacienteOrderByFechaAtencionDesc(
                        nombrePaciente, apellidoPaciente);

        if (lista.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(lista.stream().map(this::toDTO).collect(Collectors.toList()));
    }

    // Rango de las fechas .c
    @GetMapping("/rango")
    public ResponseEntity<?> rango(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {

        List<Atencion> lista = repository.findByFechaAtencionBetween(
                java.time.LocalDate.parse(fechaInicio),
                java.time.LocalDate.parse(fechaFin)
        );

        if (lista.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(lista.stream().map(this::toDTO).collect(Collectors.toList()));
    }

    // ACTUALIZARR
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody AtencionDTO dto) {

        if (!repository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        Atencion atencion = toEntity(dto);
        atencion.setId(id);
        repository.save(atencion);

        return ResponseEntity.status(201).build();
    }

    // ELIMINARR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.status(404).build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // CONVERSIONESS
    private AtencionDTO toDTO(Atencion a) {
        AtencionDTO dto = new AtencionDTO();
        dto.setId(a.getId());
        dto.setNombrePaciente(a.getNombrePaciente());
        dto.setApellidoPaciente(a.getApellidoPaciente());
        dto.setNombreMedico(a.getNombreMedico());
        dto.setApellidoMedico(a.getApellidoMedico());
        dto.setFechaAtencion(a.getFechaAtencion());
        dto.setReferencia(a.getReferencia());
        return dto;
    }

    private Atencion toEntity(AtencionDTO dto) {
        Atencion a = new Atencion();
        a.setNombrePaciente(dto.getNombrePaciente());
        a.setApellidoPaciente(dto.getApellidoPaciente());
        a.setNombreMedico(dto.getNombreMedico());
        a.setApellidoMedico(dto.getApellidoMedico());
        a.setFechaAtencion(dto.getFechaAtencion());
        a.setReferencia(dto.getReferencia());
        return a;
    }
}