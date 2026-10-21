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
import com.cesde.petmind.model.entity.CampanaDonacion;
import com.cesde.petmind.model.enums.EstadoCampana;
import com.cesde.petmind.service.CampanaDonacionService;

@RestController
@RequestMapping("/api/campanas-donacion")
public class CampanaDonacionController {
    private final CampanaDonacionService campanaDonacionService;

    @GetMapping
    public ResponseEntity<List<CampanaDonacion>> listar() {
        return ResponseEntity.ok(campanaDonacionService.listar());
    }

    // Ejemplo: GET /api/campanas-donacion/por-estado?estado=ACTIVA
    @GetMapping("/por-estado")
    public ResponseEntity<List<CampanaDonacion>> listarPorEstado(@RequestParam EstadoCampana estado) {
        return ResponseEntity.ok(campanaDonacionService.listarPorEstado(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampanaDonacion> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(campanaDonacionService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CampanaDonacion> crear(@RequestBody CampanaDonacion campana) {
        return ResponseEntity.status(HttpStatus.CREATED).body(campanaDonacionService.crear(campana));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampanaDonacion> actualizar(@PathVariable Long id, @RequestBody CampanaDonacion campana) {
        return ResponseEntity.ok(campanaDonacionService.actualizar(id, campana));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        campanaDonacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public CampanaDonacionController(final CampanaDonacionService campanaDonacionService) {
        this.campanaDonacionService = campanaDonacionService;
    }
}
