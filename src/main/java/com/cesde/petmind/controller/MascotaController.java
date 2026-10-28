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
import com.cesde.petmind.dto.MascotaRequest;
import com.cesde.petmind.dto.MascotaResponse;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.EstadoPublicacion;
import com.cesde.petmind.service.MascotaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<MascotaResponse>> listar() {
        return ResponseEntity.ok(mascotaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.obtenerPorId(id));
    }

    @GetMapping("/por-estado")
    public ResponseEntity<List<MascotaResponse>> listarPorEstado(
            @RequestParam EstadoPublicacion estadoPublicacion,
            @RequestParam EstadoAdopcion estadoAdopcion) {

        return ResponseEntity.ok(
                mascotaService.listarPorEstado(
                        estadoPublicacion,
                        estadoAdopcion));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<MascotaResponse>> buscarPorEspecieYCiudad(
            @RequestParam Especie especie,
            @RequestParam String ciudad) {

        return ResponseEntity.ok(
                mascotaService.buscarPorEspecieYCiudad(especie, ciudad));
    }

    @GetMapping("/por-fundacion")
    public ResponseEntity<List<MascotaResponse>> listarPorFundacion(
            @RequestParam Long fundacionId) {

        return ResponseEntity.ok(
                mascotaService.listarPorFundacion(fundacionId));
    }

    @PostMapping
    public ResponseEntity<MascotaResponse> crear(
            @Valid @RequestBody MascotaRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mascotaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MascotaRequest request) {

        return ResponseEntity.ok(mascotaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }
}