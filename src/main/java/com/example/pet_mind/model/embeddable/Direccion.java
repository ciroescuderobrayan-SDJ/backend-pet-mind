package com.example.pet_mind.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable 
@Getter
@Setter

public class Direccion {

    @Column(name = "calle", length = 150)
    private String calle;

    @Column(name = "ciudad", length = 100)
    private String ciudad;

    @Column(name = "pais", length = 100)
    private String pais;

    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;
}
