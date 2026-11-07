package com.cesde.petmind.controller;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

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
import com.cesde.petmind.dto.FundacionRequest;
import com.cesde.petmind.dto.FundacionResponse;
import com.cesde.petmind.service.FundacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fundaciones")
@Tag(name = "Fundaciones", description = "Administración de fundaciones y organizaciones asociadas")
public class FundacionController {
    private final FundacionService fundacionService;

    @GetMapping
    @Operation(summary = "Listar fundaciones", description = "Obtiene todas las fundaciones registradas en la app.")
    public ResponseEntity<List<FundacionResponse>> listar() {
        return ResponseEntity.ok(fundacionService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener fundación por ID", description = "Consulta una fundación por su identificador único.")
    public ResponseEntity<FundacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fundacionService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear fundación", description = "Registra una nueva fundación para la plataforma.")
    public ResponseEntity<FundacionResponse> crear(@Valid @RequestBody FundacionRequest fundacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fundacionService.crear(fundacion));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar fundación", description = "Actualiza la información de una fundación existente.")
    public ResponseEntity<FundacionResponse> actualizar(@PathVariable Long id, @Valid @RequestBody FundacionRequest fundacion) {
        return ResponseEntity.ok(fundacionService.actualizar(id, fundacion));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar fundación", description = "Elimina una fundación por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        fundacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public FundacionController(final FundacionService fundacionService) {
        this.fundacionService = fundacionService;
    }
}
