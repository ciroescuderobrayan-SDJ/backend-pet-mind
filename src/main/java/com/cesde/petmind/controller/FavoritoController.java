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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/favoritos")
@Tag(name = "Favoritos", description = "Gestión de favoritos por usuario")
public class FavoritoController {
    private final FavoritoService favoritoService;

    @GetMapping
    @Operation(summary = "Listar favoritos", description = "Obtiene todos los registros de favoritos del sistema.")
    public ResponseEntity<List<FavoritoResponse>> listar() {
        return ResponseEntity.ok(favoritoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener favorito por ID", description = "Consulta un favorito por su identificador único.")
    public ResponseEntity<FavoritoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(favoritoService.obtenerPorId(id));
    }

    // Ejemplo: GET /api/favoritos/por-usuario?usuarioId=1
    @GetMapping("/por-usuario")
    @Operation(summary = "Listar favoritos por usuario", description = "Devuelve los favoritos asociados a un usuario específico.")
    public ResponseEntity<List<FavoritoResponse>> listarPorUsuario(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(favoritoService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    @Operation(summary = "Crear favorito", description = "Agrega una mascota a los favoritos de un usuario.")
    public ResponseEntity<FavoritoResponse> crear(@Valid @RequestBody FavoritoRequest favorito) {
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritoService.crear(favorito));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar favorito", description = "Elimina un favorito por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        favoritoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    public FavoritoController(final FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }
}
