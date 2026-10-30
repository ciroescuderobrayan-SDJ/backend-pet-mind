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
import com.cesde.petmind.dto.ReporteFundacionRequest;
import com.cesde.petmind.dto.ReporteFundacionResponse;
import com.cesde.petmind.service.ReporteFundacionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reportes-fundaciones")
public class ReporteFundacionController {
    private final ReporteFundacionService reporteFundacionService;

    public ReporteFundacionController(ReporteFundacionService reporteFundacionService) {
        this.reporteFundacionService = reporteFundacionService;
    }

    @GetMapping
    public ResponseEntity<List<ReporteFundacionResponse>> listar() {
        return ResponseEntity.ok(reporteFundacionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteFundacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteFundacionService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReporteFundacionResponse> crear(@Valid @RequestBody ReporteFundacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteFundacionService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteFundacionResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ReporteFundacionRequest request) {
        return ResponseEntity.ok(reporteFundacionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reporteFundacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
