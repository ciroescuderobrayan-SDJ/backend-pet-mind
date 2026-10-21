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
import com.cesde.petmind.model.entity.SolicitudAdopcion;
import com.cesde.petmind.model.enums.EstadoSolicitud;
import com.cesde.petmind.service.SolicitudAdopcionService;

@RestController
@RequestMapping("/api/solicitudes-adopcion")
public class SolicitudAdopcionController {
    private final SolicitudAdopcionService solicitudAdopcionService;

    @GetMapping
    public ResponseEntity<List<SolicitudAdopcion>> listar() {
        return ResponseEntity.ok(solicitudAdopcionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudAdopcion> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudAdopcionService.obtenerPorId(id));
    }

    @GetMapping("/por-usuario")
    public ResponseEntity<List<SolicitudAdopcion>> listarPorUsuario(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(solicitudAdopcionService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/por-fundacion")
    public ResponseEntity<List<SolicitudAdopcion>> listarPorFundacionYEstado(@RequestParam Long fundacionId, @RequestParam EstadoSolicitud estado) {
        return ResponseEntity.ok(solicitudAdopcionService.listarPorFundacionYEstado(fundacionId, estado));
    }

    @PostMapping
    public ResponseEntity<SolicitudAdopcion> crear(@RequestBody SolicitudAdopcion solicitud) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitudAdopcionService.crear(solicitud));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudAdopcion> actualizar(@PathVariable Long id, @RequestBody SolicitudAdopcion solicitud) {
        return ResponseEntity.ok(solicitudAdopcionService.actualizar(id, solicitud));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        solicitudAdopcionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public SolicitudAdopcionController(final SolicitudAdopcionService solicitudAdopcionService) {
        this.solicitudAdopcionService = solicitudAdopcionService;
    }
}
