package com.cesde.petmind.model.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import com.cesde.petmind.model.base.BaseEntity;
import com.cesde.petmind.model.embeddable.Direccion;
import com.cesde.petmind.model.embeddable.InformacionContacto;
import com.cesde.petmind.model.enums.RolUsuario;
import com.cesde.petmind.model.enums.TipoToken;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)

@Entity
@Table(name = "usuarios")
public class Usuario extends BaseEntity {

    // Solo la llenan los usuarios con rol REPRESENTANTE_FUNDACION
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fundacion_id")
    private Fundacion fundacion;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    // Se recibe en el JSON de entrada pero nunca se devuelve en las respuestas
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "contrasena", nullable = false, length = 100)
    private String contrasena;

    @Embedded
    private InformacionContacto contacto;

    @Embedded
    private Direccion direccion;

    @Column(name = "url_avatar", length = 500)
    private String urlAvatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;

    @Builder.Default
    @Column(name = "correo_verificado", nullable = false)
    private Boolean correoVerificado = false;

    // Token de un solo uso para verificar correo o recuperar contrasena.
    // Ni el codigo ni su tipo ni su vencimiento se devuelven en el JSON: son datos internos.
    @JsonIgnore
    @Column(name = "token_codigo", length = 100)
    private String tokenCodigo;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "token_tipo")
    private TipoToken tokenTipo;

    @JsonIgnore
    @Column(name = "token_expira")
    private LocalDateTime tokenExpira;
}
