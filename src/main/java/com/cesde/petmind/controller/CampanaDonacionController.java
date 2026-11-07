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
import jakarta.validation.Valid;
import com.cesde.petmind.dto.CampanaDonacionRequest;
import com.cesde.petmind.dto.CampanaDonacionResponse;
import com.cesde.petmind.model.enums.EstadoCampana;
import com.cesde.petmind.service.CampanaDonacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/campanas-donacion")
@Tag(name = "Campañas de donación", description = "Gestión de campañas de recaudación y financiación")
public class CampanaDonacionController {
    private final CampanaDonacionService campanaDonacionService;

    @GetMapping
    @Operation(summary = "Listar campañas", description = "Obtiene todas las campañas de donación registradas en la plataforma.")
    public ResponseEntity<List<CampanaDonacionResponse>> listar() {
        return ResponseEntity.ok(campanaDonacionService.listar());
    }

    // Ejemplo: GET /api/campanas-donacion/por-estado?estado=ACTIVA
    @GetMapping("/por-estado")
    @Operation(summary = "Listar campañas por estado", description = "Filtra campañas por su estado actual, por ejemplo ACTIVA o FINALIZADA.")
    public ResponseEntity<List<CampanaDonacionResponse>> listarPorEstado(@RequestParam EstadoCampana estado) {
        return ResponseEntity.ok(campanaDonacionService.listarPorEstado(estado));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener campaña por ID", description = "Consulta una campaña de donación por su identificador único.")
    public ResponseEntity<CampanaDonacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(campanaDonacionService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear campaña", description = "Registra una nueva campaña de donación para apoyar la causa.")
    public ResponseEntity<CampanaDonacionResponse> crear(
            @Valid @RequestBody CampanaDonacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(campanaDonacionService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar campaña", description = "Actualiza la información de una campaña de donación existente.")
    public ResponseEntity<CampanaDonacionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CampanaDonacionRequest request) {
        return ResponseEntity.ok(campanaDonacionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar campaña", description = "Elimina una campaña de donación por su identificador.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        campanaDonacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public CampanaDonacionController(final CampanaDonacionService campanaDonacionService) {
        this.campanaDonacionService = campanaDonacionService;
    }
}
