package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cesde.petmind.model.embeddable.Direccion;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoReporte;
import com.cesde.petmind.model.enums.NivelUrgencia;
import com.cesde.petmind.model.enums.TipoCaso;

public record ReporteAnimalResponse(
        Long id,
        Long usuarioId,
        String codigoSeguimiento,
        TipoCaso tipoCaso,
        Especie especie,
        NivelUrgencia nivelUrgencia,
        Direccion direccion,
        BigDecimal latitud,
        BigDecimal longitud,
        String descripcion,
        String nombreContacto,
        String telefonoContacto,
        Boolean esAnonimo,
        EstadoReporte estado,
        LocalDate fechaReporte,
        Boolean estadoActivo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) {
}
