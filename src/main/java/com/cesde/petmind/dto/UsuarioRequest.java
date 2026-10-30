package com.cesde.petmind.dto;

import com.cesde.petmind.model.enums.RolUsuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        Long fundacionId,
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 100, message = "El nombre no puede superar 100 caracteres") String nombre,
        @NotBlank(message = "El apellido es obligatorio") @Size(max = 100, message = "El apellido no puede superar 100 caracteres") String apellido,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @Size(max = 100, message = "La contraseña no puede superar 100 caracteres") String contrasena,
        @NotNull(message = "El contacto es obligatorio") @Valid ContactoDto contacto,
        @Valid DireccionDto direccion,
        @Size(max = 500, message = "La URL del avatar no puede superar 500 caracteres") String urlAvatar,
        @NotNull(message = "El rol es obligatorio") RolUsuario rol) {
}
