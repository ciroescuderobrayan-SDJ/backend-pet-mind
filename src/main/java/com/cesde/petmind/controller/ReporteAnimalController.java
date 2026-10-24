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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reportes-animales")
public class ReporteAnimalController {
    private final ReporteAnimalService reporteAnimalService;

    @GetMapping
    public ResponseEntity<List<ReporteAnimalResponse>> listar() {
        return ResponseEntity.ok(reporteAnimalService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteAnimalResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteAnimalService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReporteAnimalResponse> crear(
            @Valid @RequestBody ReporteAnimalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteAnimalService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteAnimalResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ReporteAnimalRequest request) {
        return ResponseEntity.ok(reporteAnimalService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reporteAnimalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    public ReporteAnimalController(final ReporteAnimalService reporteAnimalService) {
        this.reporteAnimalService = reporteAnimalService;
    }
}
