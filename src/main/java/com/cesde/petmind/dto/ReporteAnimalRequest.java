package com.cesde.petmind.dto;

import java.math.BigDecimal;

import com.cesde.petmind.model.embeddable.Direccion;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.NivelUrgencia;
import com.cesde.petmind.model.enums.TipoCaso;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReporteAnimalRequest(
        Long usuarioId,
        @NotNull(message = "El tipo de caso es obligatorio")
        TipoCaso tipoCaso,
        @NotNull(message = "La especie es obligatoria")
        Especie especie,
        @NotNull(message = "El nivel de urgencia es obligatorio")
        NivelUrgencia nivelUrgencia,
        @NotNull(message = "La dirección es obligatoria")
        Direccion direccion,
        @Digits(integer = 3, fraction = 7,
                message = "La latitud no tiene un formato válido")
        BigDecimal latitud,
        @Digits(integer = 3, fraction = 7,
                message = "La longitud no tiene un formato válido")
        BigDecimal longitud,
        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
        String descripcion,
        @Size(max = 150, message = "El nombre de contacto no puede superar los 150 caracteres")
        String nombreContacto,
        @Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
        String telefonoContacto,
        Boolean esAnonimo) {
}
