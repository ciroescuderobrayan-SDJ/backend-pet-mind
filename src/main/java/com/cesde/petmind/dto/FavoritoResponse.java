package com.cesde.petmind.dto;

import java.time.LocalDateTime;

public record FavoritoResponse(Long id, Long usuarioId, Long mascotaId, Long campanaId,
        Long fundacionId, Boolean estadoActivo, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
}
