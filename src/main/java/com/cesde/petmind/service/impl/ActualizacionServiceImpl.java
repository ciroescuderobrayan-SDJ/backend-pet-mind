package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cesde.petmind.dto.ActualizacionRequest;
import com.cesde.petmind.dto.ActualizacionResponse;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.Actualizacion;
import com.cesde.petmind.model.entity.CampanaDonacion;
import com.cesde.petmind.model.entity.Historia;
import com.cesde.petmind.repository.ActualizacionRepository;
import com.cesde.petmind.repository.CampanaDonacionRepository;
import com.cesde.petmind.repository.HistoriaRepository;
import com.cesde.petmind.service.ActualizacionService;

@Service
@Transactional
public class ActualizacionServiceImpl implements ActualizacionService {

    private final ActualizacionRepository actualizacionRepository;
    private final CampanaDonacionRepository campanaDonacionRepository;
    private final HistoriaRepository historiaRepository;

    @Override
    public List<ActualizacionResponse> listar() {
        return actualizacionRepository.findByEstadoActivoTrue()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public ActualizacionResponse obtenerPorId(Long id) {
        Actualizacion actualizacion = buscarActivo(
                actualizacionRepository,
                id,
                "una actualización");

        return convertirAResponse(actualizacion);
    }

    @Override
    public ActualizacionResponse crear(ActualizacionRequest request) {
        Actualizacion actualizacion = convertirAEntidad(request);

        return convertirAResponse(
                actualizacionRepository.save(actualizacion));
    }

    @Override
    public ActualizacionResponse actualizar(
            Long id,
            ActualizacionRequest request) {

        Actualizacion existente = buscarActivo(
                actualizacionRepository,
                id,
                "una actualización");

        Actualizacion actualizacion = convertirAEntidad(request);

        existente.setCampana(actualizacion.getCampana());
        existente.setHistoria(actualizacion.getHistoria());
        existente.setTitulo(actualizacion.getTitulo());
        existente.setContenido(actualizacion.getContenido());
        existente.setFechaEvento(actualizacion.getFechaEvento());
        existente.setOrden(actualizacion.getOrden());

        return convertirAResponse(
                actualizacionRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        Actualizacion actualizacion = buscarActivo(
                actualizacionRepository,
                id,
                "una actualización");

        actualizacion.setEstadoActivo(false);
        actualizacionRepository.save(actualizacion);
    }

    private Actualizacion convertirAEntidad(ActualizacionRequest request) {
        validarRelaciones(request);

        Actualizacion actualizacion = new Actualizacion();
        actualizacion.setTitulo(request.titulo());
        actualizacion.setContenido(request.contenido());
        actualizacion.setFechaEvento(request.fechaEvento());
        actualizacion.setOrden(request.orden());

        if (request.campanaId() != null) {
            CampanaDonacion campana = buscarActivo(
                    campanaDonacionRepository,
                    request.campanaId(),
                    "una campaña activa");

            actualizacion.setCampana(campana);
            actualizacion.setHistoria(null);
        }

        if (request.historiaId() != null) {
            Historia historia = buscarActivo(
                    historiaRepository,
                    request.historiaId(),
                    "una historia activa");

            actualizacion.setHistoria(historia);
            actualizacion.setCampana(null);
        }

        return actualizacion;
    }

    private void validarRelaciones(ActualizacionRequest request) {
        int relaciones = 0;

        if (request.campanaId() != null) {
            relaciones++;
        }

        if (request.historiaId() != null) {
            relaciones++;
        }

        if (relaciones != 1) {
            throw new ReglaDeNegocioException(
                    "La actualización debe pertenecer a una campaña o a una historia");
        }
    }

    private ActualizacionResponse convertirAResponse(
            Actualizacion actualizacion) {

        return new ActualizacionResponse(
                actualizacion.getId(),
                actualizacion.getCampana() == null ? null : actualizacion.getCampana().getId(),
                actualizacion.getHistoria() == null ? null : actualizacion.getHistoria().getId(),
                actualizacion.getTitulo(),
                actualizacion.getContenido(),
                actualizacion.getFechaEvento(),
                actualizacion.getOrden(),
                actualizacion.getEstadoActivo(),
                actualizacion.getFechaCreacion(),
                actualizacion.getFechaActualizacion());
    }

    public ActualizacionServiceImpl(
            ActualizacionRepository actualizacionRepository,
            CampanaDonacionRepository campanaDonacionRepository,
            HistoriaRepository historiaRepository) {
        this.actualizacionRepository = actualizacionRepository;
        this.campanaDonacionRepository = campanaDonacionRepository;
        this.historiaRepository = historiaRepository;
    }
}
