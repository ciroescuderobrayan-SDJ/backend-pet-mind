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
import com.cesde.petmind.model.entity.Mascota;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.EstadoPublicacion;
import com.cesde.petmind.service.MascotaService;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {
    private final MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<Mascota>> listar() {
        return ResponseEntity.ok(mascotaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.obtenerPorId(id));
    }

    @GetMapping("/por-estado")
    public ResponseEntity<List<Mascota>> listarPorEstado(@RequestParam EstadoPublicacion estadoPublicacion, @RequestParam EstadoAdopcion estadoAdopcion) {
        return ResponseEntity.ok(mascotaService.listarPorEstado(estadoPublicacion, estadoAdopcion));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Mascota>> buscarPorEspecieYCiudad(@RequestParam Especie especie, @RequestParam String ciudad) {
        return ResponseEntity.ok(mascotaService.buscarPorEspecieYCiudad(especie, ciudad));
    }

    @GetMapping("/por-fundacion")
    public ResponseEntity<List<Mascota>> listarPorFundacion(@RequestParam Long fundacionId) {
        return ResponseEntity.ok(mascotaService.listarPorFundacion(fundacionId));
    }

    @PostMapping
    public ResponseEntity<Mascota> crear(@RequestBody Mascota mascota) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mascotaService.crear(mascota));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> actualizar(@PathVariable Long id, @RequestBody Mascota mascota) {
        return ResponseEntity.ok(mascotaService.actualizar(id, mascota));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public MascotaController(final MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }
}
