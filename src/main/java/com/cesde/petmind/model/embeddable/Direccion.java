package com.cesde.petmind.model.embeddable;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.*;
// @Getter
// @Setter

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Direccion {
    @Column(name = "calle", length = 150)
    private String calle;
    @Column(name = "ciudad", length = 100)
    private String ciudad;
    @Column(name = "pais", length = 100)
    private String pais;
    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;
    public String getCalle() {
        return this.calle;
    }
    public String getCiudad() {
        return this.ciudad;
    }
    public String getPais() {
        return this.pais;
    }
    public String getCodigoPostal() {
        return this.codigoPostal;
    }
    public void setCalle(final String calle) {
        this.calle = calle;
    }
    public void setCiudad(final String ciudad) {
        this.ciudad = ciudad;
    }
    public void setPais(final String pais) {
        this.pais = pais;
    }
    public void setCodigoPostal(final String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
