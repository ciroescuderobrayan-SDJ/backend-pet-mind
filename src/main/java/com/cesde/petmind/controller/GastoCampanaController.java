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

import com.cesde.petmind.dto.GastoCampanaRequest;
import com.cesde.petmind.dto.GastoCampanaResponse;
import com.cesde.petmind.service.GastoCampanaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/gastos-campana")
@Tag(name = "Gastos de campaña", description = "Registro y seguimiento de gastos asociados a campañas")
public class GastoCampanaController {
    private final GastoCampanaService gastoCampanaService;

    @GetMapping
    @Operation(summary = "Listar gastos", description = "Obtiene todos los gastos registrados de las campañas.")
    public ResponseEntity<List<GastoCampanaResponse>> listar() {
        return ResponseEntity.ok(gastoCampanaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener gasto por ID", description = "Consulta un gasto asociado a una campaña por su identificador.")
    public ResponseEntity<GastoCampanaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(gastoCampanaService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear gasto", description = "Registra un nuevo gasto dentro de una campaña.")
    public ResponseEntity<GastoCampanaResponse> crear(
            @Valid @RequestBody GastoCampanaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gastoCampanaService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar gasto", description = "Actualiza la información de un gasto de campaña existente.")
    public ResponseEntity<GastoCampanaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody GastoCampanaRequest request) {
        return ResponseEntity.ok(gastoCampanaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar gasto", description = "Elimina un gasto de campaña por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gastoCampanaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    public GastoCampanaController(final GastoCampanaService gastoCampanaService) {
        this.gastoCampanaService = gastoCampanaService;
    }
}
