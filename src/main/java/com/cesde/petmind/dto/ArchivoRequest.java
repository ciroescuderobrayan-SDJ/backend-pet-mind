package com.cesde.petmind.dto;

import com.cesde.petmind.model.enums.CategoriaArchivo;
import com.cesde.petmind.model.enums.TipoMedio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ArchivoRequest(Long mascotaId, Long solicitudId, Long campanaId, Long historiaId, Long reporteId,
        @NotBlank(message = "La URL es obligatoria") @Size(max = 500, message = "La URL no puede superar 500 caracteres") String url,
        @NotNull(message = "El tipo de medio es obligatorio") TipoMedio tipoMedio,
        @NotNull(message = "La categoría es obligatoria") CategoriaArchivo categoria,
        @NotNull(message = "Debe indicar si es portada") Boolean esPortada,
        @PositiveOrZero(message = "El orden no puede ser negativo") Integer orden) {
}
