package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cesde.petmind.model.enums.CategoriaCampana;
import com.cesde.petmind.model.enums.EstadoCampana;

// Lombok deshabilitado temporalmente; métodos generados manualmente abajo.
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// @Getter
// @AllArgsConstructor
public class CampanaDonacionResponse {

    private Long id;
    private Long fundacionId;
    private Long mascotaId;
    private String titulo;
    private String descripcion;
    private CategoriaCampana categoria;
    private BigDecimal metaMonto;
    private BigDecimal montoRecaudado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoCampana estado;
    private Boolean esUrgente;
    private Boolean estadoActivo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public CampanaDonacionResponse(
            Long id,
            Long fundacionId,
            Long mascotaId,
            String titulo,
            String descripcion,
            CategoriaCampana categoria,
            BigDecimal metaMonto,
            BigDecimal montoRecaudado,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            EstadoCampana estado,
            Boolean esUrgente,
            Boolean estadoActivo,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion) {
        this.id = id;
        this.fundacionId = fundacionId;
        this.mascotaId = mascotaId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.metaMonto = metaMonto;
        this.montoRecaudado = montoRecaudado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.esUrgente = esUrgente;
        this.estadoActivo = estadoActivo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getId() {
        return id;
    }

    public Long getFundacionId() {
        return fundacionId;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public CategoriaCampana getCategoria() {
        return categoria;
    }

    public BigDecimal getMetaMonto() {
        return metaMonto;
    }

    public BigDecimal getMontoRecaudado() {
        return montoRecaudado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public EstadoCampana getEstado() {
        return estado;
    }

    public Boolean getEsUrgente() {
        return esUrgente;
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
