package com.cesde.petmind.dto;

import com.cesde.petmind.model.enums.TipoNotificacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificacionRequest(
        @NotNull(message = "El usuario es obligatorio") Long usuarioId,
        @NotNull(message = "El tipo es obligatorio") TipoNotificacion tipo,
        @NotBlank(message = "El título es obligatorio") @Size(max = 150, message = "El título no puede superar 150 caracteres") String titulo,
        @NotBlank(message = "El mensaje es obligatorio") @Size(max = 500, message = "El mensaje no puede superar 500 caracteres") String mensaje,
        @Size(max = 500, message = "La URL de destino no puede superar 500 caracteres") String urlDestino,
        @NotNull(message = "Debe indicar si fue leída") Boolean leida) {
}
