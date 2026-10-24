package com.cesde.petmind.dto;

import java.math.BigDecimal;

import com.cesde.petmind.model.enums.Frecuencia;
import com.cesde.petmind.model.enums.MetodoPago;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Lombok deshabilitado temporalmente; métodos generados manualmente abajo.
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// @Getter
// @Setter
// @NoArgsConstructor
public class DonacionRequest {

    private Long campanaId;
    private Long usuarioId;
    private Long donacionOrigenId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
    @Digits(integer = 10, fraction = 2,
            message = "El monto debe tener máximo 10 enteros y 2 decimales")
    private BigDecimal monto;

    @NotNull(message = "La frecuencia es obligatoria")
    private Frecuencia frecuencia;

    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodoPago;

    private Boolean esAnonima;

    @Size(max = 500, message = "El mensaje no puede superar los 500 caracteres")
    private String mensaje;

    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    private String nombreDonante;

    @Email(message = "El correo del donante no es válido")
    @Size(max = 100, message = "El correo no puede superar los 100 caracteres")
    private String correoDonante;

    @Size(max = 30, message = "El documento no puede superar los 30 caracteres")
    private String documentoDonante;

    public DonacionRequest() {
    }

    public Long getCampanaId() {
        return campanaId;
    }

    public void setCampanaId(Long campanaId) {
        this.campanaId = campanaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getDonacionOrigenId() {
        return donacionOrigenId;
    }

    public void setDonacionOrigenId(Long donacionOrigenId) {
        this.donacionOrigenId = donacionOrigenId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Boolean getEsAnonima() {
        return esAnonima;
    }

    public void setEsAnonima(Boolean esAnonima) {
        this.esAnonima = esAnonima;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreDonante() {
        return nombreDonante;
    }

    public void setNombreDonante(String nombreDonante) {
        this.nombreDonante = nombreDonante;
    }

    public String getCorreoDonante() {
        return correoDonante;
    }

    public void setCorreoDonante(String correoDonante) {
        this.correoDonante = correoDonante;
    }

    public String getDocumentoDonante() {
        return documentoDonante;
    }

    public void setDocumentoDonante(String documentoDonante) {
        this.documentoDonante = documentoDonante;
    }
}
