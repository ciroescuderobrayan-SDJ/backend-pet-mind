package com.cesde.petmind.dto;

import com.cesde.petmind.model.embeddable.DatosHogar;

import jakarta.validation.constraints.NotNull;

public record SolicitudAdopcionRequest(
        @NotNull(message = "El usuario es obligatorio")
        Long usuarioId,

        @NotNull(message = "La mascota es obligatoria")
        Long mascotaId,

        @NotNull(message = "Los datos del hogar son obligatorios")
        DatosHogar datosHogar) {
}