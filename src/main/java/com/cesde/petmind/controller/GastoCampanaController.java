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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/gastos-campana")
public class GastoCampanaController {
    private final GastoCampanaService gastoCampanaService;

    @GetMapping
    public ResponseEntity<List<GastoCampanaResponse>> listar() {
        return ResponseEntity.ok(gastoCampanaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GastoCampanaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(gastoCampanaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<GastoCampanaResponse> crear(
            @Valid @RequestBody GastoCampanaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gastoCampanaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GastoCampanaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody GastoCampanaRequest request) {
        return ResponseEntity.ok(gastoCampanaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gastoCampanaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    public GastoCampanaController(final GastoCampanaService gastoCampanaService) {
        this.gastoCampanaService = gastoCampanaService;
    }
}
