package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cesde.petmind.model.enums.EstadoAtencion;

public record ReporteFundacionResponse(
        Long id,
        Long reporteId,
        Long fundacionId,
        BigDecimal distanciaKm,
        EstadoAtencion estadoAtencion,
        LocalDateTime fechaNotificacion,
        Boolean estadoActivo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) {
}
