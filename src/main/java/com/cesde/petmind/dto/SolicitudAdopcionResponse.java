package com.cesde.petmind.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cesde.petmind.model.embeddable.Cita;
import com.cesde.petmind.model.embeddable.DatosHogar;
import com.cesde.petmind.model.enums.EstadoSolicitud;

public record SolicitudAdopcionResponse(
        Long id,
        Long usuarioId,
        Long mascotaId,
        EstadoSolicitud estado,
        Integer porcentajeAfinidad,
        DatosHogar datosHogar,
        Cita cita,
        String notasInternas,
        LocalDate fechaSolicitud,
        LocalDateTime fechaEnRevision,
        LocalDateTime fechaEntrevista,
        LocalDateTime fechaVisitaHogar,
        LocalDateTime fechaDecision,
        Boolean estadoActivo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) {
}
