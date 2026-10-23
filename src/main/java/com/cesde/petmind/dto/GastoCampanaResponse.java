package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Lombok deshabilitado temporalmente; métodos generados manualmente abajo.
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// @Getter
// @AllArgsConstructor
public class GastoCampanaResponse {

    private Long id;
    private Long campanaId;
    private String concepto;
    private BigDecimal monto;
    private Integer orden;
    private Boolean estadoActivo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public GastoCampanaResponse(
            Long id,
            Long campanaId,
            String concepto,
            BigDecimal monto,
            Integer orden,
            Boolean estadoActivo,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion) {
        this.id = id;
        this.campanaId = campanaId;
        this.concepto = concepto;
        this.monto = monto;
        this.orden = orden;
        this.estadoActivo = estadoActivo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getId() {
        return id;
    }

    public Long getCampanaId() {
        return campanaId;
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
