package com.cesde.petmind.dto;

import java.time.LocalDateTime;
import com.cesde.petmind.model.enums.TipoNotificacion;

public record NotificacionResponse(Long id, Long usuarioId, TipoNotificacion tipo, String titulo, String mensaje,
        String urlDestino, Boolean leida, Boolean estadoActivo, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
}
