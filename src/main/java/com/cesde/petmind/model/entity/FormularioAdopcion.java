package com.cesde.petmind.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import com.cesde.petmind.model.base.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)

@Entity
@Table(name = "formularios_adopcion")
public class FormularioAdopcion extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "solicitud_id", nullable = false, unique = true)
    private SolicitudAdopcion solicitud;

    @Column(name = "tiene_otra_mascota")
    private Boolean tieneOtraMascota;

    @Column(name = "tipo_vivienda", length = 50)
    private String tipoVivienda;

    @Column(name = "es_propietario")
    private Boolean esPropietario;

    @Column(name = "motivo_adopcion", length = 500)
    private String motivoAdopcion;
}
