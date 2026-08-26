package com.example.pet_mind.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.pet_mind.model.embeddable.Direccion;
import com.example.pet_mind.model.embeddable.InformacionContacto;
import com.example.pet_mind.model.enums.RolUsuario;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)

@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;

    @Embedded
    private InformacionContacto contacto;

    @Embedded
    private Direccion direccion;
}
