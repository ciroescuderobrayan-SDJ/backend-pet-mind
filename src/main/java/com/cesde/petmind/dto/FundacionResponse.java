package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.cesde.petmind.model.enums.EstadoVerificacion;

public record FundacionResponse(Long id, String nombre, String nit, String representanteLegal,
        String descripcion, DireccionDto direccion, ContactoDto contacto, String urlLogo,
        String urlPortada, String urlRut, String urlCamaraComercio, EstadoVerificacion estadoVerificacion,
        LocalDateTime fechaVerificacion, BigDecimal latitud, BigDecimal longitud, Boolean estadoActivo,
        LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
}
