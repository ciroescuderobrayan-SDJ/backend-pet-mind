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
import com.cesde.petmind.dto.ReporteFundacionRequest;
import com.cesde.petmind.dto.ReporteFundacionResponse;
import com.cesde.petmind.service.ReporteFundacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reportes-fundaciones")
@Tag(name = "Reportes de fundaciones", description = "Reportes y estadísticas de fundaciones")
public class ReporteFundacionController {
    private final ReporteFundacionService reporteFundacionService;

    public ReporteFundacionController(ReporteFundacionService reporteFundacionService) {
        this.reporteFundacionService = reporteFundacionService;
    }

    @GetMapping
    @Operation(summary = "Listar reportes de fundaciones", description = "Obtiene todos los reportes asociados a fundaciones.")
    public ResponseEntity<List<ReporteFundacionResponse>> listar() {
        return ResponseEntity.ok(reporteFundacionService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reporte de fundación por ID", description = "Consulta un reporte por su identificador único.")
    public ResponseEntity<ReporteFundacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteFundacionService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear reporte de fundación", description = "Registra un nuevo reporte de la actividad de una fundación.")
    public ResponseEntity<ReporteFundacionResponse> crear(@Valid @RequestBody ReporteFundacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteFundacionService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reporte de fundación", description = "Actualiza un reporte existente de una fundación.")
    public ResponseEntity<ReporteFundacionResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ReporteFundacionRequest request) {
        return ResponseEntity.ok(reporteFundacionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reporte de fundación", description = "Elimina un reporte por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reporteFundacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
