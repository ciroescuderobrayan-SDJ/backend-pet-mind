package com.cesde.petmind.model.embeddable;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.Getter;
// import lombok.Setter;
// @Getter
// @Setter

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Afinidad {
    @Column(name = "nivel_energia")
    private Integer nivelEnergia;
    @Column(name = "afinidad_perros")
    private Integer afinidadPerros;
    @Column(name = "afinidad_gatos")
    private Integer afinidadGatos;
    @Column(name = "afinidad_ninos")
    private Integer afinidadNinos;
    public Integer getNivelEnergia() {
        return this.nivelEnergia;
    }
    public Integer getAfinidadPerros() {
        return this.afinidadPerros;
    }
    public Integer getAfinidadGatos() {
        return this.afinidadGatos;
    }
    public Integer getAfinidadNinos() {
        return this.afinidadNinos;
    }
    public void setNivelEnergia(final Integer nivelEnergia) {
        this.nivelEnergia = nivelEnergia;
    }
    public void setAfinidadPerros(final Integer afinidadPerros) {
        this.afinidadPerros = afinidadPerros;
    }
    public void setAfinidadGatos(final Integer afinidadGatos) {
        this.afinidadGatos = afinidadGatos;
    }
    public void setAfinidadNinos(final Integer afinidadNinos) {
        this.afinidadNinos = afinidadNinos;
    }
}
