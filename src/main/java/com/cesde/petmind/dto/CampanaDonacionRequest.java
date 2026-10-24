package com.cesde.petmind.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.cesde.petmind.model.enums.CategoriaCampana;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Lombok deshabilitado temporalmente; métodos generados manualmente abajo.
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// @Getter
// @Setter
// @NoArgsConstructor
public class CampanaDonacionRequest {

    @NotNull(message = "La fundación es obligatoria")
    private Long fundacionId;

    private Long mascotaId;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 150, message = "El título no puede superar los 150 caracteres")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
    private String descripcion;

    @NotNull(message = "La categoría es obligatoria")
    private CategoriaCampana categoria;

    @NotNull(message = "La meta de monto es obligatoria")
    @DecimalMin(value = "0.01", message = "La meta debe ser mayor que cero")
    @Digits(integer = 10, fraction = 2,
            message = "La meta debe tener máximo 10 enteros y 2 decimales")
    private BigDecimal metaMonto;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private Boolean esUrgente;

    public CampanaDonacionRequest() {
    }

    public Long getFundacionId() {
        return fundacionId;
    }

    public void setFundacionId(Long fundacionId) {
        this.fundacionId = fundacionId;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CategoriaCampana getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaCampana categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getMetaMonto() {
        return metaMonto;
    }

    public void setMetaMonto(BigDecimal metaMonto) {
        this.metaMonto = metaMonto;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getEsUrgente() {
        return esUrgente;
    }

    public void setEsUrgente(Boolean esUrgente) {
        this.esUrgente = esUrgente;
    }
}
