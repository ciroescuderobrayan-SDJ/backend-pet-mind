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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cesde.petmind.dto.ArchivoRequest;
import com.cesde.petmind.dto.ArchivoResponse;
import com.cesde.petmind.service.ArchivoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/archivos")
public class ArchivoController {
    private final ArchivoService archivoService;

    @GetMapping
    public ResponseEntity<List<ArchivoResponse>> listar() {
        return ResponseEntity.ok(archivoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArchivoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(archivoService.obtenerPorId(id));
    }

    // Ejemplo: GET /api/archivos/por-mascota?mascotaId=1
    @GetMapping("/por-mascota")
    public ResponseEntity<List<ArchivoResponse>> listarPorMascota(@RequestParam Long mascotaId) {
        return ResponseEntity.ok(archivoService.listarPorMascota(mascotaId));
    }

    @PostMapping
    public ResponseEntity<ArchivoResponse> crear(@Valid @RequestBody ArchivoRequest archivo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(archivoService.crear(archivo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArchivoResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ArchivoRequest archivo) {
        return ResponseEntity.ok(archivoService.actualizar(id, archivo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        archivoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public ArchivoController(final ArchivoService archivoService) {
        this.archivoService = archivoService;
    }
}
