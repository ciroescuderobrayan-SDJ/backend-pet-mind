package com.cesde.petmind.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class InformacionContacto {

    @Column(name = "telefono_principal", length = 20)
    private String telefonoPrincipal;

    @Column(name = "telefono_secundario", length = 20)
    private String telefonoSecundario;

    @Column(name = "email_contacto", nullable = false, unique = true, length = 100)
    private String emailContacto;

    @Column(name = "contrasena", length = 15)
    private String contrasena;
}
