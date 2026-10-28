package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cesde.petmind.model.enums.EstadoAtencion;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public record ReporteFundacionRequest(
        @NotNull(message = "El reporte animal es obligatorio")
        Long reporteId,

        @NotNull(message = "La fundación es obligatoria")
        Long fundacionId,

        @DecimalMin(value = "0.0", inclusive = true,
                message = "La distancia no puede ser negativa")
        @Digits(integer = 10, fraction = 2,
                message = "La distancia debe tener máximo 10 enteros y 2 decimales")
        BigDecimal distanciaKm,

        @NotNull(message = "El estado de atención es obligatorio")
        EstadoAtencion estadoAtencion,

        LocalDateTime fechaNotificacion) {
}