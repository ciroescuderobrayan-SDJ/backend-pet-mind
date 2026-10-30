package com.cesde.petmind.dto;

import java.time.LocalDateTime;
import com.cesde.petmind.model.enums.RolUsuario;

public record UsuarioResponse(Long id, Long fundacionId, String nombre, String apellido,
        ContactoDto contacto, DireccionDto direccion, String urlAvatar, RolUsuario rol,
        Boolean correoVerificado, Boolean estadoActivo, LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) {
}
