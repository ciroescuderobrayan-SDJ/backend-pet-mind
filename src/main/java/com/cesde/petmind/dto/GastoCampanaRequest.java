package com.cesde.petmind.dto;

import java.math.BigDecimal;

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
public class GastoCampanaRequest {

    @NotNull(message = "La campaña es obligatoria")
    private Long campanaId;

    @NotBlank(message = "El concepto es obligatorio")
    @Size(max = 250, message = "El concepto no puede superar los 250 caracteres")
    private String concepto;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
    @Digits(integer = 10, fraction = 2, message = "El monto debe tener máximo 10 enteros y 2 decimales")
    private BigDecimal monto;

    private Integer orden;

    public GastoCampanaRequest() {
    }

    public Long getCampanaId() {
        return campanaId;
    }

    public void setCampanaId(Long campanaId) {
        this.campanaId = campanaId;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
}
