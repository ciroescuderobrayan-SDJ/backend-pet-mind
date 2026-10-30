package com.cesde.petmind.dto;

import com.cesde.petmind.model.embeddable.Direccion;
import jakarta.validation.constraints.Size;

public record DireccionDto(
        @Size(max = 150, message = "La calle no puede superar 150 caracteres") String calle,
        @Size(max = 100, message = "La ciudad no puede superar 100 caracteres") String ciudad,
        @Size(max = 100, message = "El país no puede superar 100 caracteres") String pais,
        @Size(max = 10, message = "El código postal no puede superar 10 caracteres") String codigoPostal) {

    public Direccion aEntidad() {
        Direccion direccion = new Direccion();
        direccion.setCalle(calle);
        direccion.setCiudad(ciudad);
        direccion.setPais(pais);
        direccion.setCodigoPostal(codigoPostal);
        return direccion;
    }

    public static DireccionDto desde(Direccion direccion) {
        return direccion == null ? null : new DireccionDto(direccion.getCalle(), direccion.getCiudad(),
                direccion.getPais(), direccion.getCodigoPostal());
    }
}
