package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GastoCampanaResponse(
        String concepto,
        BigDecimal monto,
        Integer orden,
        Boolean estadoActivo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) {
}
