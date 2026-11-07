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
import com.cesde.petmind.dto.HistoriaRequest;
import com.cesde.petmind.dto.HistoriaResponse;
import com.cesde.petmind.service.HistoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/historias")
@Tag(name = "Historias", description = "Historias y relatos asociados a mascotas o adopciones")
public class HistoriaController {
    private final HistoriaService historiaService;

    public HistoriaController(HistoriaService historiaService) {
        this.historiaService = historiaService;
    }

    @GetMapping
    @Operation(summary = "Listar historias", description = "Obtiene todas las historias registradas en la plataforma.")
    public ResponseEntity<List<HistoriaResponse>> listar() {
        return ResponseEntity.ok(historiaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener historia por ID", description = "Consulta una historia por su identificador único.")
    public ResponseEntity<HistoriaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(historiaService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear historia", description = "Registra una nueva historia asociada a un animal o adopción.")
    public ResponseEntity<HistoriaResponse> crear(@Valid @RequestBody HistoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(historiaService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar historia", description = "Actualiza una historia existente.")
    public ResponseEntity<HistoriaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody HistoriaRequest request) {
        return ResponseEntity.ok(historiaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar historia", description = "Elimina una historia por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        historiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
