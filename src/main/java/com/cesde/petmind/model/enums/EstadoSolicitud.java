package com.cesde.petmind.model.enums;

import java.util.EnumSet;
import java.util.Set;

public enum EstadoSolicitud {
    APROBADA,
    RECHAZADA,
    PENDIENTE,
    BORRADOR,
    ENVIADA,
    EN_REVISION,
    ENTREVISTA,
    VISITA_HOGAR,
    CANCELADA,
    // La fundacion pidio mas documentos; vuelve a EN_REVISION cuando la persona responde
    DOCUMENTOS_PENDIENTES;

    // Solicitudes que todavia esperan una decision de la fundacion
    public static final Set<EstadoSolicitud> ABIERTOS =
            EnumSet.of(PENDIENTE, ENVIADA, EN_REVISION, ENTREVISTA, VISITA_HOGAR, DOCUMENTOS_PENDIENTES);

    // Estados a los que la fundacion puede mover una solicitud
    public static final Set<EstadoSolicitud> DECISIONES_FUNDACION =
            EnumSet.of(EN_REVISION, ENTREVISTA, VISITA_HOGAR, DOCUMENTOS_PENDIENTES, APROBADA, RECHAZADA);

    public boolean estaCerrada() {
        return this == APROBADA || this == RECHAZADA || this == CANCELADA;
    }
}
