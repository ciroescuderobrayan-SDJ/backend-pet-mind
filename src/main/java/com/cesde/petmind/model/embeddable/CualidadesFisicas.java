package com.cesde.petmind.model.embeddable;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.Getter;
// import lombok.Setter;
// @Getter
// @Setter

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CualidadesFisicas {
    @Column(name = "peso_kg")
    private Double pesoKg;
    @Column(name = "altura_cm")
    private Double alturaCm;
    @Column(name = "color_pelaje", length = 50)
    private String colorPelaje;
    // Para alergias, enfermedades previas, tratamientos activos, etc.
    @Column(name = "condiciones_medicas", length = 500)
    private String condicionesMedicas;
    @Column(name = "cuidados_especiales", length = 500)
    private String cuidadosEspeciales;
    public Double getPesoKg() {
        return this.pesoKg;
    }
    public Double getAlturaCm() {
        return this.alturaCm;
    }
    public String getColorPelaje() {
        return this.colorPelaje;
    }
    public String getCondicionesMedicas() {
        return this.condicionesMedicas;
    }
    public String getCuidadosEspeciales() {
        return this.cuidadosEspeciales;
    }
    public void setPesoKg(final Double pesoKg) {
        this.pesoKg = pesoKg;
    }
    public void setAlturaCm(final Double alturaCm) {
        this.alturaCm = alturaCm;
    }
    public void setColorPelaje(final String colorPelaje) {
        this.colorPelaje = colorPelaje;
    }
    public void setCondicionesMedicas(final String condicionesMedicas) {
        this.condicionesMedicas = condicionesMedicas;
    }
    public void setCuidadosEspeciales(final String cuidadosEspeciales) {
        this.cuidadosEspeciales = cuidadosEspeciales;
    }
}
