package com.cesde.petmind.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ActualizacionRequest(
        Long campanaId,

        Long historiaId,

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 200, message = "El título no puede superar los 200 caracteres")
        String titulo,

        @NotBlank(message = "El contenido es obligatorio")
        @Size(max = 5000, message = "El contenido no puede superar los 5000 caracteres")
        String contenido,

        @NotNull(message = "La fecha del evento es obligatoria")
        LocalDateTime fechaEvento,

        @PositiveOrZero(message = "El orden no puede ser negativo")
        Integer orden) {
}