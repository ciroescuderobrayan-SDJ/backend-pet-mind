package com.cesde.petmind.dto;

import com.cesde.petmind.model.enums.EstadoSolicitud;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// La fundacion mueve la solicitud: revision, pedir documentos, aprobar o rechazar.
// usuarioId es quien toma la decision (representante de la fundacion o administrador).
public record CambioEstadoSolicitudRequest(
        @NotNull(message = "El usuario que toma la decisión es obligatorio")
        Long usuarioId,

        @NotNull(message = "El estado es obligatorio")
        EstadoSolicitud estado,

        @Size(max = 1000, message = "El mensaje no puede superar 1000 caracteres")
        String mensaje) {
}
