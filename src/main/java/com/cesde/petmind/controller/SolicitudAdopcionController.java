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
import com.cesde.petmind.dto.SolicitudAdopcionRequest;
import com.cesde.petmind.dto.SolicitudAdopcionResponse;
import com.cesde.petmind.model.enums.EstadoSolicitud;
import com.cesde.petmind.service.SolicitudAdopcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/solicitudes-adopcion")
@Tag(name = "Solicitudes de adopción", description = "Gestión de solicitudes y aprobaciones de adopción")
public class SolicitudAdopcionController {

    private final SolicitudAdopcionService solicitudAdopcionService;

    @GetMapping
    @Operation(summary = "Listar solicitudes", description = "Obtiene todas las solicitudes de adopción registradas.")
    public ResponseEntity<List<SolicitudAdopcionResponse>> listar() {
        return ResponseEntity.ok(solicitudAdopcionService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener solicitud por ID", description = "Consulta una solicitud de adopción por su identificador único.")
    public ResponseEntity<SolicitudAdopcionResponse> obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                solicitudAdopcionService.obtenerPorId(id));
    }

    @GetMapping("/por-usuario")
    @Operation(summary = "Listar solicitudes por usuario", description = "Devuelve las solicitudes de adopción asociadas a un usuario concreto.")
    public ResponseEntity<List<SolicitudAdopcionResponse>> listarPorUsuario(
            @RequestParam Long usuarioId) {

        return ResponseEntity.ok(
                solicitudAdopcionService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/por-fundacion")
    @Operation(summary = "Listar solicitudes por fundación y estado", description = "Filtra solicitudes por fundación y estado de la adopción.")
    public ResponseEntity<List<SolicitudAdopcionResponse>>
            listarPorFundacionYEstado(
                    @RequestParam Long fundacionId,
                    @RequestParam EstadoSolicitud estado) {

        return ResponseEntity.ok(
                solicitudAdopcionService
                        .listarPorFundacionYEstado(fundacionId, estado));
    }

    @PostMapping
    @Operation(summary = "Crear solicitud", description = "Registra una nueva solicitud de adopción para una mascota.")
    public ResponseEntity<SolicitudAdopcionResponse> crear(
            @Valid @RequestBody SolicitudAdopcionRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(solicitudAdopcionService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar solicitud", description = "Actualiza una solicitud de adopción existente.")
    public ResponseEntity<SolicitudAdopcionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudAdopcionRequest request) {

        return ResponseEntity.ok(
                solicitudAdopcionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar solicitud", description = "Elimina una solicitud de adopción por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        solicitudAdopcionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    public SolicitudAdopcionController(
            SolicitudAdopcionService solicitudAdopcionService) {
        this.solicitudAdopcionService = solicitudAdopcionService;
    }
}