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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cesde.petmind.dto.FavoritoRequest;
import com.cesde.petmind.dto.FavoritoResponse;
import com.cesde.petmind.service.FavoritoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {
    private final FavoritoService favoritoService;

    @GetMapping
    public ResponseEntity<List<FavoritoResponse>> listar() {
        return ResponseEntity.ok(favoritoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoritoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(favoritoService.obtenerPorId(id));
    }

    // Ejemplo: GET /api/favoritos/por-usuario?usuarioId=1
    @GetMapping("/por-usuario")
    public ResponseEntity<List<FavoritoResponse>> listarPorUsuario(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(favoritoService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<FavoritoResponse> crear(@Valid @RequestBody FavoritoRequest favorito) {
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritoService.crear(favorito));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        favoritoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public FavoritoController(final FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }
}
