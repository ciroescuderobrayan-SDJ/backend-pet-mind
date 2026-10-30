package com.cesde.petmind.dto;

import com.cesde.petmind.model.embeddable.InformacionContacto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactoDto(
        @Size(max = 20, message = "El teléfono principal no puede superar 20 caracteres") String telefonoPrincipal,
        @Size(max = 20, message = "El teléfono secundario no puede superar 20 caracteres") String telefonoSecundario,
        @NotBlank(message = "El correo es obligatorio")
        @Size(max = 100, message = "El correo no puede superar 100 caracteres") String emailContacto) {

    public InformacionContacto aEntidad() {
        InformacionContacto contacto = new InformacionContacto();
        contacto.setTelefonoPrincipal(telefonoPrincipal);
        contacto.setTelefonoSecundario(telefonoSecundario);
        contacto.setEmailContacto(emailContacto);
        return contacto;
    }

    public static ContactoDto desde(InformacionContacto contacto) {
        return contacto == null ? null : new ContactoDto(contacto.getTelefonoPrincipal(),
                contacto.getTelefonoSecundario(), contacto.getEmailContacto());
    }
}
