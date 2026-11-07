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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/archivos")
@Tag(name = "Archivos", description = "Gestión de archivos asociados a mascotas y publicaciones")
public class ArchivoController {
    private final ArchivoService archivoService;

    @GetMapping
    @Operation(summary = "Listar archivos", description = "Obtiene todos los archivos almacenados en la plataforma.")
    public ResponseEntity<List<ArchivoResponse>> listar() {
        return ResponseEntity.ok(archivoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener archivo por ID", description = "Consulta un archivo específico mediante su identificador.")
    public ResponseEntity<ArchivoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(archivoService.obtenerPorId(id));
    }

    // Ejemplo: GET /api/archivos/por-mascota?mascotaId=1
    @GetMapping("/por-mascota")
    @Operation(summary = "Listar archivos por mascota", description = "Devuelve los archivos asociados a una mascota concreta.")
    public ResponseEntity<List<ArchivoResponse>> listarPorMascota(@RequestParam Long mascotaId) {
        return ResponseEntity.ok(archivoService.listarPorMascota(mascotaId));
    }

    @PostMapping
    @Operation(summary = "Crear archivo", description = "Registra un nuevo archivo relacionado con una mascota o publicación.")
    public ResponseEntity<ArchivoResponse> crear(@Valid @RequestBody ArchivoRequest archivo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(archivoService.crear(archivo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar archivo", description = "Actualiza la información de un archivo existente.")
    public ResponseEntity<ArchivoResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ArchivoRequest archivo) {
        return ResponseEntity.ok(archivoService.actualizar(id, archivo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar archivo", description = "Elimina un archivo por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        archivoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public ArchivoController(final ArchivoService archivoService) {
        this.archivoService = archivoService;
    }
}
