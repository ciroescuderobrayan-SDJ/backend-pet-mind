package com.cesde.petmind.dto;

import com.cesde.petmind.model.enums.TipoHistoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HistoriaRequest(
        @NotNull(message = "La fundación es obligatoria")
        Long fundacionId,

        Long mascotaId,

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 200, message = "El título no puede superar los 200 caracteres")
        String titulo,

        @NotBlank(message = "El resumen es obligatorio")
        @Size(max = 500, message = "El resumen no puede superar los 500 caracteres")
        String resumen,

        @NotBlank(message = "El contenido es obligatorio")
        @Size(max = 5000, message = "El contenido no puede superar los 5000 caracteres")
        String contenido,

        @NotNull(message = "El tipo de historia es obligatorio")
        TipoHistoria tipo,

        Boolean esDestacada) {
}