package com.cesde.petmind.dto;

import com.cesde.petmind.model.embeddable.Afinidad;
import com.cesde.petmind.model.embeddable.CualidadesFisicas;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.EstadoPublicacion;
import com.cesde.petmind.model.enums.SexoMascota;
import com.cesde.petmind.model.enums.TamanoMascota;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record MascotaRequest(
        @NotNull(message = "La fundación es obligatoria")
        Long fundacionId,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @NotNull(message = "La especie es obligatoria")
        Especie especie,

        @Size(max = 100, message = "La raza no puede superar los 100 caracteres")
        String raza,

        @PositiveOrZero(message = "La edad en meses no puede ser negativa")
        Integer edadMeses,

        TamanoMascota tamano,
        SexoMascota sexo,
        CualidadesFisicas cualidadesFisicas,

        @Size(max = 100, message = "La ciudad no puede superar los 100 caracteres")
        String ciudad,

        @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
        String descripcion,

        @Size(max = 2000, message = "La historia no puede superar los 2000 caracteres")
        String historia,

        @Size(max = 500, message = "Las etiquetas no pueden superar los 500 caracteres")
        String etiquetas,

        Afinidad afinidad,

        @NotNull(message = "El estado de adopción es obligatorio")
        EstadoAdopcion estadoAdopcion,

        @NotNull(message = "El estado de publicación es obligatorio")
        EstadoPublicacion estadoPublicacion,

        @NotNull(message = "Debe indicar si está esterilizado")
        Boolean esterilizado,

        @NotNull(message = "Debe indicar si tiene las vacunas al día")
        Boolean vacunasAlDia,

        @NotNull(message = "Debe indicar si está desparasitado")
        Boolean desparasitado,

        @NotNull(message = "Debe indicar si tiene microchip")
        Boolean tieneMicrochip,

        @NotNull(message = "Debe indicar si es apto para apadrinamiento")
        Boolean aptoApadrinamiento) {
}