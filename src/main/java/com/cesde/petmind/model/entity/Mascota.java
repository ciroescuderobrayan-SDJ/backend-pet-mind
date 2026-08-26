package com.cesde.petmind.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import com.cesde.petmind.model.base.BaseEntity;
import com.cesde.petmind.model.embeddable.CualidadesFisicas;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.SexoMascota;
import com.cesde.petmind.model.enums.TamanoMascota;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)

@Entity
@Table(name = "mascotas")
public class Mascota extends BaseEntity {

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "especie", nullable = false)
    private Especie especie;

    @Column(name = "raza", length = 100)
    private String raza;

    @Column(name = "edad_aproximada")
    private Integer edadAproximada;

    @Enumerated(EnumType.STRING)
    @Column(name = "tamano")
    private TamanoMascota tamano;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private SexoMascota sexo;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_adopcion", nullable = false)
    private EstadoAdopcion estadoAdopcion;

    @Column(name = "esterilizado")
    private Boolean esterilizado;

    @Embedded
    private CualidadesFisicas cualidadesFisicas;
}
