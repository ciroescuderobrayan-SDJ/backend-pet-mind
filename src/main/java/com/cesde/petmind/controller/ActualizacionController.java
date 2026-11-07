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
import com.cesde.petmind.dto.ActualizacionRequest;
import com.cesde.petmind.dto.ActualizacionResponse;
import com.cesde.petmind.service.ActualizacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/actualizaciones")
@Tag(name = "Actualizaciones", description = "Seguimiento de actualizaciones de animales o perfiles")
public class ActualizacionController {
    private final ActualizacionService actualizacionService;

    public ActualizacionController(ActualizacionService actualizacionService) {
        this.actualizacionService = actualizacionService;
    }

    @GetMapping
    @Operation(summary = "Listar actualizaciones", description = "Obtiene todas las actualizaciones registradas en la plataforma.")
    public ResponseEntity<List<ActualizacionResponse>> listar() {
        return ResponseEntity.ok(actualizacionService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener actualización por ID", description = "Consulta una actualización por su identificador único.")
    public ResponseEntity<ActualizacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(actualizacionService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear actualización", description = "Registra una nueva actualización del contenido asociado.")
    public ResponseEntity<ActualizacionResponse> crear(@Valid @RequestBody ActualizacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(actualizacionService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar actualización", description = "Actualiza una actualización existente.")
    public ResponseEntity<ActualizacionResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ActualizacionRequest request) {
        return ResponseEntity.ok(actualizacionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar actualización", description = "Elimina una actualización por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        actualizacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
