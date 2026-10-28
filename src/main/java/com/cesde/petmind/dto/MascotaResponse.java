package com.cesde.petmind.dto;

import java.time.LocalDateTime;

import com.cesde.petmind.model.embeddable.Afinidad;
import com.cesde.petmind.model.embeddable.CualidadesFisicas;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.EstadoPublicacion;
import com.cesde.petmind.model.enums.SexoMascota;
import com.cesde.petmind.model.enums.TamanoMascota;

public record MascotaResponse(
        String nombre,
        Especie especie,
        String raza,
        Integer edadMeses,
        TamanoMascota tamano,
        SexoMascota sexo,
        CualidadesFisicas cualidadesFisicas,
        String ciudad,
        String descripcion,
        String historia,
        String etiquetas,
        Afinidad afinidad,
        EstadoAdopcion estadoAdopcion,
        EstadoPublicacion estadoPublicacion,
        Boolean esterilizado,
        Boolean vacunasAlDia,
        Boolean desparasitado,
        Boolean tieneMicrochip,
        Boolean aptoApadrinamiento,
        LocalDateTime fechaPublicacion,
        Integer contadorVisitas,
        Boolean estadoActivo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion) {
}