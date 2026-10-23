package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cesde.petmind.model.enums.EstadoDonacion;
import com.cesde.petmind.model.enums.EstadoRecurrencia;
import com.cesde.petmind.model.enums.Frecuencia;
import com.cesde.petmind.model.enums.MetodoPago;

// Lombok deshabilitado temporalmente; métodos generados manualmente abajo.
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// @Getter
// @AllArgsConstructor
public class DonacionResponse {

    private Long id;
    private Long campanaId;
    private Long usuarioId;
    private Long donacionOrigenId;
    private BigDecimal monto;
    private BigDecimal valorComision;
    private BigDecimal montoTotal;
    private Frecuencia frecuencia;
    private LocalDate fechaDonacion;
    private MetodoPago metodoPago;
    private EstadoDonacion estado;
    private Boolean esAnonima;
    private String mensaje;
    private String referenciaPago;
    private String nombreDonante;
    private String correoDonante;
    private String documentoDonante;
    private EstadoRecurrencia estadoRecurrencia;
    private Integer diaCobro;
    private LocalDate fechaProximoCobro;
    private String certificadoCodigo;
    private String certificadoUrl;
    private LocalDate certificadoFecha;
    private Boolean estadoActivo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public DonacionResponse(
            Long id,
            Long campanaId,
            Long usuarioId,
            Long donacionOrigenId,
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
        this.id = id;
        this.campanaId = campanaId;
        this.usuarioId = usuarioId;
        this.donacionOrigenId = donacionOrigenId;
        this.monto = monto;
        this.valorComision = valorComision;
        this.montoTotal = montoTotal;
        this.frecuencia = frecuencia;
        this.fechaDonacion = fechaDonacion;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.esAnonima = esAnonima;
        this.mensaje = mensaje;
        this.referenciaPago = referenciaPago;
        this.nombreDonante = nombreDonante;
        this.correoDonante = correoDonante;
        this.documentoDonante = documentoDonante;
        this.estadoRecurrencia = estadoRecurrencia;
        this.diaCobro = diaCobro;
        this.fechaProximoCobro = fechaProximoCobro;
        this.certificadoCodigo = certificadoCodigo;
        this.certificadoUrl = certificadoUrl;
        this.certificadoFecha = certificadoFecha;
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getDonacionOrigenId() {
        return donacionOrigenId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public BigDecimal getValorComision() {
        return valorComision;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public LocalDate getFechaDonacion() {
        return fechaDonacion;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public EstadoDonacion getEstado() {
        return estado;
    }

    public Boolean getEsAnonima() {
        return esAnonima;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getReferenciaPago() {
        return referenciaPago;
    }

    public String getNombreDonante() {
        return nombreDonante;
    }

    public String getCorreoDonante() {
        return correoDonante;
    }

    public String getDocumentoDonante() {
        return documentoDonante;
    }

    public EstadoRecurrencia getEstadoRecurrencia() {
        return estadoRecurrencia;
    }

    public Integer getDiaCobro() {
        return diaCobro;
    }

    public LocalDate getFechaProximoCobro() {
        return fechaProximoCobro;
    }

    public String getCertificadoCodigo() {
        return certificadoCodigo;
    }

    public String getCertificadoUrl() {
        return certificadoUrl;
    }

    public LocalDate getCertificadoFecha() {
        return certificadoFecha;
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
