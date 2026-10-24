package com.cesde.petmind.dto;

import java.math.BigDecimal;

import com.cesde.petmind.model.enums.Frecuencia;
import com.cesde.petmind.model.enums.MetodoPago;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DonacionRequest(
        Long campanaId,
        Long usuarioId,
        Long donacionOrigenId,
        @NotNull(message = "El monto es obligatorio")
        @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
        @Digits(integer = 10, fraction = 2,
                message = "El monto debe tener máximo 10 enteros y 2 decimales")
        BigDecimal monto,
        @NotNull(message = "La frecuencia es obligatoria")
        Frecuencia frecuencia,
        @NotNull(message = "El método de pago es obligatorio")
        MetodoPago metodoPago,
        Boolean esAnonima,
        @Size(max = 500, message = "El mensaje no puede superar los 500 caracteres")
        String mensaje,
        @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
        String nombreDonante,
        @Email(message = "El correo del donante no es válido")
        @Size(max = 100, message = "El correo no puede superar los 100 caracteres")
        String correoDonante,
        @Size(max = 30, message = "El documento no puede superar los 30 caracteres")
        String documentoDonante) {
}
