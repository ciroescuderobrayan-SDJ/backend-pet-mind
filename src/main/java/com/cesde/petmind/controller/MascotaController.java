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
import com.cesde.petmind.dto.MascotaRequest;
import com.cesde.petmind.dto.MascotaResponse;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.EstadoPublicacion;
import com.cesde.petmind.service.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mascotas")
@Tag(name = "Mascotas", description = "Gestión de mascotas, filtros y adopciones")
public class MascotaController {

    private final MascotaService mascotaService;

    @GetMapping
    @Operation(summary = "Listar mascotas", description = "Obtiene todas las mascotas registradas en la plataforma.")
    public ResponseEntity<List<MascotaResponse>> listar() {
        return ResponseEntity.ok(mascotaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener mascota por ID", description = "Consulta una mascota por su identificador único.")
    public ResponseEntity<MascotaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.obtenerPorId(id));
    }

    @GetMapping("/por-estado")
    @Operation(summary = "Listar mascotas por estado", description = "Filtra mascotas por estado de publicación y estado de adopción.")
    public ResponseEntity<List<MascotaResponse>> listarPorEstado(
            @RequestParam EstadoPublicacion estadoPublicacion,
            @RequestParam EstadoAdopcion estadoAdopcion) {

        return ResponseEntity.ok(
                mascotaService.listarPorEstado(
                        estadoPublicacion,
                        estadoAdopcion));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar mascotas por especie y ciudad", description = "Busca mascotas según especie y ciudad de residencia.")
    public ResponseEntity<List<MascotaResponse>> buscarPorEspecieYCiudad(
            @RequestParam Especie especie,
            @RequestParam String ciudad) {

        return ResponseEntity.ok(
                mascotaService.buscarPorEspecieYCiudad(especie, ciudad));
    }

    @GetMapping("/por-fundacion")
    @Operation(summary = "Listar mascotas por fundación", description = "Devuelve las mascotas asociadas a una fundación específica.")
    public ResponseEntity<List<MascotaResponse>> listarPorFundacion(
            @RequestParam Long fundacionId) {

        return ResponseEntity.ok(
                mascotaService.listarPorFundacion(fundacionId));
    }

    @PostMapping
    @Operation(summary = "Registrar mascota", description = "Crea una nueva mascota en la plataforma.")
    public ResponseEntity<MascotaResponse> crear(
            @Valid @RequestBody MascotaRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mascotaService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar mascota", description = "Actualiza los datos de una mascota existente.")
    public ResponseEntity<MascotaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MascotaRequest request) {

        return ResponseEntity.ok(mascotaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mascota", description = "Elimina una mascota del sistema por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }
}