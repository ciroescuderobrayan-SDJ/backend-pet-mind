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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/historias")
public class HistoriaController {
    private final HistoriaService historiaService;

    public HistoriaController(HistoriaService historiaService) {
        this.historiaService = historiaService;
    }

    @GetMapping
    public ResponseEntity<List<HistoriaResponse>> listar() {
        return ResponseEntity.ok(historiaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(historiaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<HistoriaResponse> crear(@Valid @RequestBody HistoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(historiaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoriaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody HistoriaRequest request) {
        return ResponseEntity.ok(historiaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        historiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
