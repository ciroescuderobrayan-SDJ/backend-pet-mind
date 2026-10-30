package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cesde.petmind.model.enums.CategoriaCampana;
import com.cesde.petmind.model.enums.EstadoCampana;

public record CampanaDonacionResponse(
        Long id,
        Long fundacionId,
        Long mascotaId,
        String titulo,
        String descripcion,
        CategoriaCampana categoria,
        BigDecimal metaMonto,
        BigDecimal montoRecaudado,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        EstadoCampana estado,
        Boolean esUrgente,
        Boolean estadoActivo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) {
}
