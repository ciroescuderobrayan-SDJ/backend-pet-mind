package com.cesde.petmind.dto;

import java.time.LocalDateTime;
import com.cesde.petmind.model.enums.CategoriaArchivo;
import com.cesde.petmind.model.enums.TipoMedio;

public record ArchivoResponse(Long id, Long mascotaId, Long solicitudId, Long campanaId, Long historiaId,
        Long reporteId, String url, TipoMedio tipoMedio, CategoriaArchivo categoria, Boolean esPortada,
        Integer orden, Boolean estadoActivo, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
}
