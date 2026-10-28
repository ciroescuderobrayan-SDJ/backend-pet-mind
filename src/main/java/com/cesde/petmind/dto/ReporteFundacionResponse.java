package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cesde.petmind.model.enums.EstadoAtencion;

public record ReporteFundacionResponse(
        BigDecimal distanciaKm,
        EstadoAtencion estadoAtencion,
        LocalDateTime fechaNotificacion,
        Boolean estadoActivo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) {
}