package com.cesde.petmind.dto;

import java.time.LocalDateTime;

import com.cesde.petmind.model.enums.TipoHistoria;

public record HistoriaResponse(
        Long id,
        Long fundacionId,
        Long mascotaId,
        String titulo,
        String resumen,
        String contenido,
        TipoHistoria tipo,
        Integer contadorLikes,
        Boolean esDestacada,
        LocalDateTime fechaPublicacion,
        Boolean estadoActivo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) {
}
