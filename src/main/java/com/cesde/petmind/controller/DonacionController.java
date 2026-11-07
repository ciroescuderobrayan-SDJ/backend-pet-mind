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
import com.cesde.petmind.dto.DonacionRequest;
import com.cesde.petmind.dto.DonacionResponse;
import com.cesde.petmind.model.enums.EstadoDonacion;
import com.cesde.petmind.service.DonacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/donaciones")
@Tag(name = "Donaciones", description = "Gestión de donaciones y registros por campaña")
public class DonacionController {
    private final DonacionService donacionService;

    @GetMapping
    @Operation(summary = "Listar donaciones", description = "Obtiene todas las donaciones registradas en el sistema.")
    public ResponseEntity<List<DonacionResponse>> listar() {
        return ResponseEntity.ok(donacionService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener donación por ID", description = "Consulta una donación por su identificador único.")
    public ResponseEntity<DonacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(donacionService.obtenerPorId(id));
    }

    // Ejemplo: GET /api/donaciones/por-usuario?usuarioId=1
    @GetMapping("/por-usuario")
    @Operation(summary = "Listar donaciones por usuario", description = "Devuelve las donaciones asociadas a un usuario concreto.")
    public ResponseEntity<List<DonacionResponse>> listarPorUsuario(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(donacionService.listarPorUsuario(usuarioId));
    }

    // Ejemplo: GET /api/donaciones/por-campana?campanaId=1&estado=COMPLETADA
    @GetMapping("/por-campana")
    @Operation(summary = "Listar donaciones por campaña y estado", description = "Filtra donaciones según la campaña y el estado de la donación.")
    public ResponseEntity<List<DonacionResponse>> listarPorCampanaYEstado(@RequestParam Long campanaId, @RequestParam EstadoDonacion estado) {
        return ResponseEntity.ok(donacionService.listarPorCampanaYEstado(campanaId, estado));
    }

    @PostMapping
    @Operation(summary = "Crear donación", description = "Registra una nueva donación en la plataforma.")
    public ResponseEntity<DonacionResponse> crear(@Valid @RequestBody DonacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donacionService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar donación", description = "Actualiza una donación existente.")
    public ResponseEntity<DonacionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DonacionRequest request) {
        return ResponseEntity.ok(donacionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar donación", description = "Elimina una donación por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        donacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public DonacionController(final DonacionService donacionService) {
        this.donacionService = donacionService;
    }
}
