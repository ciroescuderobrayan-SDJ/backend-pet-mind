package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cesde.petmind.model.enums.EstadoDonacion;
import com.cesde.petmind.model.enums.EstadoRecurrencia;
import com.cesde.petmind.model.enums.Frecuencia;
import com.cesde.petmind.model.enums.MetodoPago;

public record DonacionResponse(
        BigDecimal monto,
        BigDecimal valorComision,
        BigDecimal montoTotal,
        Frecuencia frecuencia,
        LocalDate fechaDonacion,
        MetodoPago metodoPago,
        EstadoDonacion estado,
        Boolean esAnonima,
        String mensaje,
        String referenciaPago,
        String nombreDonante,
        String correoDonante,
        String documentoDonante,
        EstadoRecurrencia estadoRecurrencia,
        Integer diaCobro,
        LocalDate fechaProximoCobro,
        String certificadoCodigo,
        String certificadoUrl,
        LocalDate certificadoFecha,
        Boolean estadoActivo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) {
}
