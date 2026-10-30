package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.cesde.petmind.model.enums.EstadoVerificacion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FundacionRequest(
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 150, message = "El nombre no puede superar 150 caracteres") String nombre,
        @NotBlank(message = "El NIT es obligatorio") @Size(max = 20, message = "El NIT no puede superar 20 caracteres") String nit,
        @NotBlank(message = "El representante legal es obligatorio") @Size(max = 150, message = "El representante legal no puede superar 150 caracteres") String representanteLegal,
        @Size(max = 1000, message = "La descripción no puede superar 1000 caracteres") String descripcion,
        @Valid DireccionDto direccion,
        @NotNull(message = "El contacto es obligatorio") @Valid ContactoDto contacto,
        @Size(max = 500, message = "La URL del logo no puede superar 500 caracteres") String urlLogo,
        @Size(max = 500, message = "La URL de portada no puede superar 500 caracteres") String urlPortada,
        @Size(max = 500, message = "La URL del RUT no puede superar 500 caracteres") String urlRut,
        @Size(max = 500, message = "La URL de cámara de comercio no puede superar 500 caracteres") String urlCamaraComercio,
        @NotNull(message = "El estado de verificación es obligatorio") EstadoVerificacion estadoVerificacion,
        LocalDateTime fechaVerificacion,
        @Digits(integer = 3, fraction = 7, message = "La latitud admite hasta 3 enteros y 7 decimales") BigDecimal latitud,
        @Digits(integer = 3, fraction = 7, message = "La longitud admite hasta 3 enteros y 7 decimales") BigDecimal longitud) {
}
