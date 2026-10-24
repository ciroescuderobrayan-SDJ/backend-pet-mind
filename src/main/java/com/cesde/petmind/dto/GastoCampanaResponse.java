package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Lombok deshabilitado temporalmente; métodos generados manualmente abajo.
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// @Getter
// @AllArgsConstructor
public class GastoCampanaResponse {

    private String concepto;
    private BigDecimal monto;
    private Integer orden;
    private Boolean estadoActivo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public GastoCampanaResponse(
            String concepto,
            BigDecimal monto,
            Integer orden,
            Boolean estadoActivo,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion) {
        this.concepto = concepto;
        this.monto = monto;
        this.orden = orden;
        this.estadoActivo = estadoActivo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getConcepto() {
        return concepto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public Integer getOrden() {
        return orden;
    }

    public Boolean getEstadoActivo() {
        return estadoActivo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
}
