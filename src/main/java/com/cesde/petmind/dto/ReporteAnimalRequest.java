package com.cesde.petmind.dto;

import java.math.BigDecimal;

import com.cesde.petmind.model.embeddable.Direccion;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.NivelUrgencia;
import com.cesde.petmind.model.enums.TipoCaso;

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
public class ReporteAnimalRequest {

    private Long usuarioId;

    @NotNull(message = "El tipo de caso es obligatorio")
    private TipoCaso tipoCaso;

    @NotNull(message = "La especie es obligatoria")
    private Especie especie;

    @NotNull(message = "El nivel de urgencia es obligatorio")
    private NivelUrgencia nivelUrgencia;

    @NotNull(message = "La dirección es obligatoria")
    private Direccion direccion;

    @Digits(integer = 3, fraction = 7,
            message = "La latitud no tiene un formato válido")
    private BigDecimal latitud;

    @Digits(integer = 3, fraction = 7,
            message = "La longitud no tiene un formato válido")
    private BigDecimal longitud;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
    private String descripcion;

    @Size(max = 150, message = "El nombre de contacto no puede superar los 150 caracteres")
    private String nombreContacto;

    @Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
    private String telefonoContacto;

    private Boolean esAnonimo;

    public ReporteAnimalRequest() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public TipoCaso getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(TipoCaso tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public NivelUrgencia getNivelUrgencia() {
        return nivelUrgencia;
    }

    public void setNivelUrgencia(NivelUrgencia nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public Boolean getEsAnonimo() {
        return esAnonimo;
    }

    public void setEsAnonimo(Boolean esAnonimo) {
        this.esAnonimo = esAnonimo;
    }
}
