package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GastoCampanaResponse(
        Long id,
        Long campanaId,
        String concepto,
        BigDecimal monto,
        Integer orden,
        Boolean estadoActivo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) {
}
