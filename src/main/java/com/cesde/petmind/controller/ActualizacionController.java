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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/actualizaciones")
public class ActualizacionController {
    private final ActualizacionService actualizacionService;

    public ActualizacionController(ActualizacionService actualizacionService) {
        this.actualizacionService = actualizacionService;
    }

    @GetMapping
    public ResponseEntity<List<ActualizacionResponse>> listar() {
        return ResponseEntity.ok(actualizacionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActualizacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(actualizacionService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ActualizacionResponse> crear(@Valid @RequestBody ActualizacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(actualizacionService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActualizacionResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ActualizacionRequest request) {
        return ResponseEntity.ok(actualizacionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        actualizacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
