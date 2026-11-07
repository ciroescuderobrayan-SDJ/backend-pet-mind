package com.cesde.petmind.controller;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cesde.petmind.dto.NotificacionRequest;
import com.cesde.petmind.dto.NotificacionResponse;
import com.cesde.petmind.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Notificaciones", description = "Gestión de notificaciones y estado de lectura")
public class NotificacionController {
    private final NotificacionService notificacionService;

    @GetMapping
    @Operation(summary = "Listar notificaciones", description = "Obtiene todas las notificaciones del sistema.")
    public ResponseEntity<List<NotificacionResponse>> listar() {
        return ResponseEntity.ok(notificacionService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener notificación por ID", description = "Consulta una notificación específica mediante su identificador.")
    public ResponseEntity<NotificacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.obtenerPorId(id));
    }

    // Ejemplo: GET /api/notificaciones/no-leidas?usuarioId=1
    @GetMapping("/no-leidas")
    @Operation(summary = "Listar notificaciones no leídas", description = "Devuelve las notificaciones pendientes de lectura para un usuario.")
    public ResponseEntity<List<NotificacionResponse>> listarNoLeidasPorUsuario(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(notificacionService.listarNoLeidasPorUsuario(usuarioId));
    }

    @PostMapping
    @Operation(summary = "Crear notificación", description = "Genera una nueva notificación para un usuario.")
    public ResponseEntity<NotificacionResponse> crear(@Valid @RequestBody NotificacionRequest notificacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.crear(notificacion));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar notificación", description = "Actualiza el contenido o estado de una notificación existente.")
    public ResponseEntity<NotificacionResponse> actualizar(@PathVariable Long id, @Valid @RequestBody NotificacionRequest notificacion) {
        return ResponseEntity.ok(notificacionService.actualizar(id, notificacion));
    }

    @PatchMapping("/{id}/leida")
    @Operation(summary = "Marcar notificación como leída", description = "Actualiza el estado de una notificación para marcarla como leída.")
    public ResponseEntity<NotificacionResponse> marcarComoLeida(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.marcarComoLeida(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar notificación", description = "Elimina una notificación por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public NotificacionController(final NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }
}
