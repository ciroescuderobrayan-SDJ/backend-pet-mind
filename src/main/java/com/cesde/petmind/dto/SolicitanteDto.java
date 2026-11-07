package com.cesde.petmind.dto;

// Datos de contacto de quien envia la solicitud, para que la fundacion pueda revisarla.
public record SolicitanteDto(String nombre, String apellido, String correo, String telefono, String ciudad) {
}
