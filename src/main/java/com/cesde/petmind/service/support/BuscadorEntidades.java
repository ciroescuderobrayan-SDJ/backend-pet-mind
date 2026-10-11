package com.cesde.petmind.service.support;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cesde.petmind.exceptions.RecursoNoEncontradoException;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.base.BaseEntity;

// Busca un registro activo por id. Todos los servicios repetian el mismo bloque:
// findById(...).orElse(null) + "si no existe o esta inactivo, lanzar 404".
public final class BuscadorEntidades {

    private BuscadorEntidades() {
    }

    /**
     * Devuelve el registro activo con ese id.
     *
     * @param recurso como se nombra en el mensaje, por ejemplo "una mascota" o "una fundacion activa"
     * @throws ReglaDeNegocioException       si el id es null (el JSON trajo la relacion sin id): 400
     * @throws RecursoNoEncontradoException si no existe o tiene borrado logico: 404
     */
    public static <T extends BaseEntity> T buscarActivo(JpaRepository<T, Long> repositorio, Long id, String recurso) {
        // Sin esta validacion, findById(null) lanza IllegalArgumentException y la API respondia 500
        if (id == null) {
            throw new ReglaDeNegocioException("Falta el id de " + recurso);
        }
        return repositorio.findById(id)
                .filter(BaseEntity::getEstadoActivo)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe " + recurso + " con id " + id));
    }
}
