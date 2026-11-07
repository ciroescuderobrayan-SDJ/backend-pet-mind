package com.cesde.petmind.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cesde.petmind.dto.LoginRequest;
import com.cesde.petmind.dto.UsuarioResponse;
import com.cesde.petmind.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Inicio de sesión de usuarios")
public class AuthController {
    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Todavia no hay tokens: el frontend guarda el usuario que devuelve este endpoint en su sesion.
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Valida el correo y la contraseña y devuelve el usuario. Responde 401 si no coinciden.")
    public ResponseEntity<UsuarioResponse> iniciarSesion(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(usuarioService.iniciarSesion(request));
    }
}
