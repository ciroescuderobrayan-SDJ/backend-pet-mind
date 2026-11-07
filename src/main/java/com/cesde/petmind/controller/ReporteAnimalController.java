package com.cesde.petmind.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cesde.petmind.dto.ReporteAnimalRequest;
import com.cesde.petmind.dto.ReporteAnimalResponse;
import com.cesde.petmind.service.ReporteAnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reportes-animales")
@Tag(name = "Reportes de animales", description = "Reporte y seguimiento de animales en la plataforma")
public class ReporteAnimalController {
    private final ReporteAnimalService reporteAnimalService;

    @GetMapping
    @Operation(summary = "Listar reportes de animales", description = "Obtiene todos los reportes registrados sobre animales.")
    public ResponseEntity<List<ReporteAnimalResponse>> listar() {
        return ResponseEntity.ok(reporteAnimalService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reporte de animal por ID", description = "Consulta un reporte por su identificador único.")
    public ResponseEntity<ReporteAnimalResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteAnimalService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear reporte de animal", description = "Registra un nuevo reporte relacionado con un animal.")
    public ResponseEntity<ReporteAnimalResponse> crear(
            @Valid @RequestBody ReporteAnimalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteAnimalService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reporte de animal", description = "Actualiza un reporte de animal existente.")
    public ResponseEntity<ReporteAnimalResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ReporteAnimalRequest request) {
        return ResponseEntity.ok(reporteAnimalService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reporte de animal", description = "Elimina un reporte de animal por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reporteAnimalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    public ReporteAnimalController(final ReporteAnimalService reporteAnimalService) {
        this.reporteAnimalService = reporteAnimalService;
    }
}
