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
import com.cesde.petmind.model.entity.Donacion;
import com.cesde.petmind.model.enums.EstadoDonacion;
import com.cesde.petmind.service.DonacionService;

@RestController
@RequestMapping("/api/donaciones")
public class DonacionController {
    private final DonacionService donacionService;

    @GetMapping
    public ResponseEntity<List<Donacion>> listar() {
        return ResponseEntity.ok(donacionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donacion> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(donacionService.obtenerPorId(id));
    }

    // Ejemplo: GET /api/donaciones/por-usuario?usuarioId=1
    @GetMapping("/por-usuario")
    public ResponseEntity<List<Donacion>> listarPorUsuario(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(donacionService.listarPorUsuario(usuarioId));
    }

    // Ejemplo: GET /api/donaciones/por-campana?campanaId=1&estado=COMPLETADA
    @GetMapping("/por-campana")
    public ResponseEntity<List<Donacion>> listarPorCampanaYEstado(@RequestParam Long campanaId, @RequestParam EstadoDonacion estado) {
        return ResponseEntity.ok(donacionService.listarPorCampanaYEstado(campanaId, estado));
    }

    @PostMapping
    public ResponseEntity<Donacion> crear(@RequestBody Donacion donacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donacionService.crear(donacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donacion> actualizar(@PathVariable Long id, @RequestBody Donacion donacion) {
        return ResponseEntity.ok(donacionService.actualizar(id, donacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        donacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public DonacionController(final DonacionService donacionService) {
        this.donacionService = donacionService;
    }
}
