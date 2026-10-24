package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.cesde.petmind.model.enums.CategoriaCampana;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

        public record CampanaDonacionRequest(
        @NotNull(message = "La fundación es obligatoria")
        Long fundacionId,
        Long mascotaId,
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 150, message = "El título no puede superar los 150 caracteres")
        String titulo,
        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
        String descripcion,
        @NotNull(message = "La categoría es obligatoria")
        CategoriaCampana categoria,
        @NotNull(message = "La meta de monto es obligatoria")
        @DecimalMin(value = "0.01", message = "La meta debe ser mayor que cero")
        @Digits(integer = 10, fraction = 2,
                message = "La meta debe tener máximo 10 enteros y 2 decimales")
        BigDecimal metaMonto,
        @NotNull(message = "La fecha de inicio es obligatoria")
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Boolean esUrgente) {
}
