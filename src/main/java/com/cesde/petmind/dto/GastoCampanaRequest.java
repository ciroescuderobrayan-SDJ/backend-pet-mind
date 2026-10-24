package com.cesde.petmind.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GastoCampanaRequest(
        @NotNull(message = "La campaña es obligatoria")
        Long campanaId,
        @NotBlank(message = "El concepto es obligatorio")
        @Size(max = 250, message = "El concepto no puede superar los 250 caracteres")
        String concepto,
        @NotNull(message = "El monto es obligatorio")
        @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
        @Digits(integer = 10, fraction = 2,
                message = "El monto debe tener máximo 10 enteros y 2 decimales")
        BigDecimal monto,
        Integer orden) {
}
