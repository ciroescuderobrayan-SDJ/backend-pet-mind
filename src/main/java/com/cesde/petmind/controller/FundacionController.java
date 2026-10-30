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
import org.springframework.web.bind.annotation.RestController;
import com.cesde.petmind.dto.FundacionRequest;
import com.cesde.petmind.dto.FundacionResponse;
import com.cesde.petmind.service.FundacionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fundaciones")
public class FundacionController {
    private final FundacionService fundacionService;

    @GetMapping
    public ResponseEntity<List<FundacionResponse>> listar() {
        return ResponseEntity.ok(fundacionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FundacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fundacionService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<FundacionResponse> crear(@Valid @RequestBody FundacionRequest fundacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fundacionService.crear(fundacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FundacionResponse> actualizar(@PathVariable Long id, @Valid @RequestBody FundacionRequest fundacion) {
        return ResponseEntity.ok(fundacionService.actualizar(id, fundacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        fundacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public FundacionController(final FundacionService fundacionService) {
        this.fundacionService = fundacionService;
    }
}
