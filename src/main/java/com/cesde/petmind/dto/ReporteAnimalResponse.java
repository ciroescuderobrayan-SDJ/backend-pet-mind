package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cesde.petmind.model.embeddable.Direccion;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoReporte;
import com.cesde.petmind.model.enums.NivelUrgencia;
import com.cesde.petmind.model.enums.TipoCaso;

// Lombok deshabilitado temporalmente; métodos generados manualmente abajo.
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// @Getter
// @AllArgsConstructor
public class ReporteAnimalResponse {

    private String codigoSeguimiento;
    private TipoCaso tipoCaso;
    private Especie especie;
    private NivelUrgencia nivelUrgencia;
    private Direccion direccion;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String descripcion;
    private String nombreContacto;
    private String telefonoContacto;
    private Boolean esAnonimo;
    private EstadoReporte estado;
    private LocalDate fechaReporte;
    private Boolean estadoActivo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public ReporteAnimalResponse(
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
        this.codigoSeguimiento = codigoSeguimiento;
        this.tipoCaso = tipoCaso;
        this.especie = especie;
        this.nivelUrgencia = nivelUrgencia;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
        this.nombreContacto = nombreContacto;
        this.telefonoContacto = telefonoContacto;
        this.esAnonimo = esAnonimo;
        this.estado = estado;
        this.fechaReporte = fechaReporte;
        this.estadoActivo = estadoActivo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getCodigoSeguimiento() {
        return codigoSeguimiento;
    }

    public TipoCaso getTipoCaso() {
        return tipoCaso;
    }

    public Especie getEspecie() {
        return especie;
    }

    public NivelUrgencia getNivelUrgencia() {
        return nivelUrgencia;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public Boolean getEsAnonimo() {
        return esAnonimo;
    }

    public EstadoReporte getEstado() {
        return estado;
    }

    public LocalDate getFechaReporte() {
        return fechaReporte;
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
