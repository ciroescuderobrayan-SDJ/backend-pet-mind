package com.cesde.petmind.dto;

import jakarta.validation.constraints.NotNull;

public record FavoritoRequest(
        @NotNull(message = "El usuario es obligatorio") Long usuarioId,
        Long mascotaId,
        Long campanaId,
        Long fundacionId) {
}
