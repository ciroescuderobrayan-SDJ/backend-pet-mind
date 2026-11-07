package com.cesde.petmind.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// La persona responde cuando la fundacion le pide documentos.
public record RespuestaSolicitudRequest(
        @NotNull(message = "El usuario es obligatorio")
        Long usuarioId,

        @NotBlank(message = "La respuesta es obligatoria")
        @Size(max = 2000, message = "La respuesta no puede superar 2000 caracteres")
        String respuesta) {
}
