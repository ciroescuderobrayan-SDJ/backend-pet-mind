package com.cesde.petmind.model.embeddable;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.Getter;
// import lombok.Setter;
// @Getter
// @Setter

import com.cesde.petmind.model.enums.TipoVivienda;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class DatosHogar {
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vivienda")
    private TipoVivienda tipoVivienda;
    @Column(name = "es_propietario")
    private Boolean esPropietario;
    @Column(name = "tiene_patio")
    private Boolean tienePatio;
    @Column(name = "numero_convivientes")
    private Integer numeroConvivientes;
    @Column(name = "tiene_otra_mascota")
    private Boolean tieneOtraMascota;
    @Column(name = "descripcion_otras_mascotas", length = 500)
    private String descripcionOtrasMascotas;
    @Column(name = "experiencia_previa", length = 500)
    private String experienciaPrevia;
    @Column(name = "horas_disponibles_dia")
    private Integer horasDisponiblesDia;
    @Column(name = "motivo_adopcion", length = 500)
    private String motivoAdopcion;
    @Column(name = "acepta_compromisos")
    private Boolean aceptaCompromisos;
    @Column(name = "autoriza_datos")
    private Boolean autorizaDatos;
    public TipoVivienda getTipoVivienda() {
        return this.tipoVivienda;
    }
    public Boolean getEsPropietario() {
        return this.esPropietario;
    }
    public Boolean getTienePatio() {
        return this.tienePatio;
    }
    public Integer getNumeroConvivientes() {
        return this.numeroConvivientes;
    }
    public Boolean getTieneOtraMascota() {
        return this.tieneOtraMascota;
    }
    public String getDescripcionOtrasMascotas() {
        return this.descripcionOtrasMascotas;
    }
    public String getExperienciaPrevia() {
        return this.experienciaPrevia;
    }
    public Integer getHorasDisponiblesDia() {
        return this.horasDisponiblesDia;
    }
    public String getMotivoAdopcion() {
        return this.motivoAdopcion;
    }
    public Boolean getAceptaCompromisos() {
        return this.aceptaCompromisos;
    }
    public Boolean getAutorizaDatos() {
        return this.autorizaDatos;
    }
    public void setTipoVivienda(final TipoVivienda tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }
    public void setEsPropietario(final Boolean esPropietario) {
        this.esPropietario = esPropietario;
    }
    public void setTienePatio(final Boolean tienePatio) {
        this.tienePatio = tienePatio;
    }
    public void setNumeroConvivientes(final Integer numeroConvivientes) {
        this.numeroConvivientes = numeroConvivientes;
    }
    public void setTieneOtraMascota(final Boolean tieneOtraMascota) {
        this.tieneOtraMascota = tieneOtraMascota;
    }
    public void setDescripcionOtrasMascotas(final String descripcionOtrasMascotas) {
        this.descripcionOtrasMascotas = descripcionOtrasMascotas;
    }
    public void setExperienciaPrevia(final String experienciaPrevia) {
        this.experienciaPrevia = experienciaPrevia;
    }
    public void setHorasDisponiblesDia(final Integer horasDisponiblesDia) {
        this.horasDisponiblesDia = horasDisponiblesDia;
    }
    public void setMotivoAdopcion(final String motivoAdopcion) {
        this.motivoAdopcion = motivoAdopcion;
    }
    public void setAceptaCompromisos(final Boolean aceptaCompromisos) {
        this.aceptaCompromisos = aceptaCompromisos;
    }
    public void setAutorizaDatos(final Boolean autorizaDatos) {
        this.autorizaDatos = autorizaDatos;
    }
}
