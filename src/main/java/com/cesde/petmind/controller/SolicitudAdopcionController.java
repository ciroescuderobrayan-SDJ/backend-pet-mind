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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/solicitudes-adopcion")
public class SolicitudAdopcionController {

    private final SolicitudAdopcionService solicitudAdopcionService;

    @GetMapping
    public ResponseEntity<List<SolicitudAdopcionResponse>> listar() {
        return ResponseEntity.ok(solicitudAdopcionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudAdopcionResponse> obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                solicitudAdopcionService.obtenerPorId(id));
    }

    @GetMapping("/por-usuario")
    public ResponseEntity<List<SolicitudAdopcionResponse>> listarPorUsuario(
            @RequestParam Long usuarioId) {

        return ResponseEntity.ok(
                solicitudAdopcionService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/por-fundacion")
    public ResponseEntity<List<SolicitudAdopcionResponse>>
            listarPorFundacionYEstado(
                    @RequestParam Long fundacionId,
                    @RequestParam EstadoSolicitud estado) {

        return ResponseEntity.ok(
                solicitudAdopcionService
                        .listarPorFundacionYEstado(fundacionId, estado));
    }

    @PostMapping
    public ResponseEntity<SolicitudAdopcionResponse> crear(
            @Valid @RequestBody SolicitudAdopcionRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(solicitudAdopcionService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudAdopcionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudAdopcionRequest request) {

        return ResponseEntity.ok(
                solicitudAdopcionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        solicitudAdopcionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    public SolicitudAdopcionController(
            SolicitudAdopcionService solicitudAdopcionService) {
        this.solicitudAdopcionService = solicitudAdopcionService;
    }
}