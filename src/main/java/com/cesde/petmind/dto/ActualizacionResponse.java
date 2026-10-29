package com.cesde.petmind.dto;

import java.time.LocalDateTime;

public record ActualizacionResponse(
        String titulo,
        String contenido,
        LocalDateTime fechaEvento,
        Integer orden,
        Boolean estadoActivo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) {
}