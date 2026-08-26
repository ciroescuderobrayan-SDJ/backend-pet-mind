package com.example.pet_mind.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
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
}
