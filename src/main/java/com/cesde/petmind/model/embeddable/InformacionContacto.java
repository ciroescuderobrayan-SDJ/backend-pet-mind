package com.cesde.petmind.model.embeddable;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.Getter;
// import lombok.Setter;
// @Getter
// @Setter

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class InformacionContacto {
    @Column(name = "telefono_principal", length = 20)
    private String telefonoPrincipal;
    @Column(name = "telefono_secundario", length = 20)
    private String telefonoSecundario;
    @Column(name = "email_contacto", nullable = false, unique = true, length = 100)
    private String emailContacto;
    public String getTelefonoPrincipal() {
        return this.telefonoPrincipal;
    }
    public String getTelefonoSecundario() {
        return this.telefonoSecundario;
    }
    public String getEmailContacto() {
        return this.emailContacto;
    }
    public void setTelefonoPrincipal(final String telefonoPrincipal) {
        this.telefonoPrincipal = telefonoPrincipal;
    }
    public void setTelefonoSecundario(final String telefonoSecundario) {
        this.telefonoSecundario = telefonoSecundario;
    }
    public void setEmailContacto(final String emailContacto) {
        this.emailContacto = emailContacto;
    }
}
