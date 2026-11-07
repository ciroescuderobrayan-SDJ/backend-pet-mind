package com.cesde.petmind.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cesde.petmind.model.entity.SolicitudAdopcion;
import com.cesde.petmind.model.enums.EstadoSolicitud;

public interface SolicitudAdopcionRepository extends JpaRepository<SolicitudAdopcion, Long> {

    // Borrado logico: los listados solo traen los registros activos
    List<SolicitudAdopcion> findByEstadoActivoTrue();

    // Panel del usuario
    List<SolicitudAdopcion> findByUsuarioIdAndEstadoActivoTrue(Long usuarioId);

    // Tablero de la fundacion: recorre solicitud -> mascota -> fundacion
    List<SolicitudAdopcion> findByMascotaFundacionIdAndEstadoAndEstadoActivoTrue(
            Long fundacionId,
            EstadoSolicitud estado);

    // Tablero de la fundacion con todas sus solicitudes, sin filtrar por estado
    List<SolicitudAdopcion> findByMascotaFundacionIdAndEstadoActivoTrue(Long fundacionId);

    // Una persona no puede tener dos solicitudes abiertas para la misma mascota
    boolean existsByUsuarioIdAndMascotaIdAndEstadoInAndEstadoActivoTrue(
            Long usuarioId,
            Long mascotaId,
            Collection<EstadoSolicitud> estados);

    // Al aprobar una adopcion se cierran las demas solicitudes abiertas de esa mascota
    List<SolicitudAdopcion> findByMascotaIdAndEstadoInAndEstadoActivoTrue(
            Long mascotaId,
            Collection<EstadoSolicitud> estados);
}
