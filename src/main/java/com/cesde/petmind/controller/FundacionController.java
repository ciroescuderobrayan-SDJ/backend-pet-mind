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
import com.cesde.petmind.model.entity.Fundacion;
import com.cesde.petmind.service.FundacionService;

@RestController
@RequestMapping("/api/fundaciones")
public class FundacionController {
    private final FundacionService fundacionService;

    @GetMapping
    public ResponseEntity<List<Fundacion>> listar() {
        return ResponseEntity.ok(fundacionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fundacion> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fundacionService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Fundacion> crear(@RequestBody Fundacion fundacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fundacionService.crear(fundacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fundacion> actualizar(@PathVariable Long id, @RequestBody Fundacion fundacion) {
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
