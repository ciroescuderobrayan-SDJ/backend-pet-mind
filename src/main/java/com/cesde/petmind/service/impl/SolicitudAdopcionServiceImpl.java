package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cesde.petmind.dto.SolicitudAdopcionRequest;
import com.cesde.petmind.dto.SolicitudAdopcionResponse;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.Mascota;
import com.cesde.petmind.model.entity.SolicitudAdopcion;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.EstadoSolicitud;
import com.cesde.petmind.repository.MascotaRepository;
import com.cesde.petmind.repository.SolicitudAdopcionRepository;
import com.cesde.petmind.repository.UsuarioRepository;
import com.cesde.petmind.service.SolicitudAdopcionService;

@Service
@Transactional
public class SolicitudAdopcionServiceImpl implements SolicitudAdopcionService {

    private final SolicitudAdopcionRepository solicitudAdopcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;

    @Override
    public List<SolicitudAdopcionResponse> listar() {
        return solicitudAdopcionRepository.findByEstadoActivoTrue()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public SolicitudAdopcionResponse obtenerPorId(Long id) {
        SolicitudAdopcion solicitud = buscarActivo(
                solicitudAdopcionRepository,
                id,
                "una solicitud de adopción");

        return convertirAResponse(solicitud);
    }

    @Override
    public SolicitudAdopcionResponse crear(SolicitudAdopcionRequest request) {
        SolicitudAdopcion solicitud = convertirAEntidad(request);

        validarMascotaDisponible(solicitud.getMascota());

        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setFechaSolicitud(LocalDate.now());

        return convertirAResponse(solicitudAdopcionRepository.save(solicitud));
    }

    @Override
    public SolicitudAdopcionResponse actualizar(
            Long id,
            SolicitudAdopcionRequest request) {

        SolicitudAdopcion existente = buscarActivo(
                solicitudAdopcionRepository,
                id,
                "una solicitud de adopción");

        SolicitudAdopcion solicitud = convertirAEntidad(request);

        existente.setUsuario(solicitud.getUsuario());
        existente.setMascota(solicitud.getMascota());
        existente.setDatosHogar(solicitud.getDatosHogar());

        return convertirAResponse(solicitudAdopcionRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        SolicitudAdopcion solicitud = buscarActivo(
                solicitudAdopcionRepository,
                id,
                "una solicitud de adopción");

        solicitud.setEstadoActivo(false);
        solicitudAdopcionRepository.save(solicitud);
    }

    @Override
    public List<SolicitudAdopcionResponse> listarPorUsuario(Long usuarioId) {
        return solicitudAdopcionRepository.findByUsuarioIdAndEstadoActivoTrue(usuarioId)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public List<SolicitudAdopcionResponse> listarPorFundacionYEstado(
            Long fundacionId,
            EstadoSolicitud estado) {

        return solicitudAdopcionRepository
                .findByMascotaFundacionIdAndEstadoAndEstadoActivoTrue(
                        fundacionId,
                        estado)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    private SolicitudAdopcion convertirAEntidad(SolicitudAdopcionRequest request) {
        Usuario usuario = buscarActivo(
                usuarioRepository,
                request.usuarioId(),
                "un usuario activo");

        Mascota mascota = buscarActivo(
                mascotaRepository,
                request.mascotaId(),
                "una mascota activa");

        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setUsuario(usuario);
        solicitud.setMascota(mascota);
        solicitud.setDatosHogar(request.datosHogar());

        return solicitud;
    }

    private void validarMascotaDisponible(Mascota mascota) {
        if (mascota.getEstadoAdopcion() != EstadoAdopcion.DISPONIBLE) {
            throw new ReglaDeNegocioException(
                    "La mascota no está disponible para adopción");
        }
    }

    private SolicitudAdopcionResponse convertirAResponse(
            SolicitudAdopcion solicitud) {

        return new SolicitudAdopcionResponse(
                solicitud.getId(),
                solicitud.getUsuario() == null ? null : solicitud.getUsuario().getId(),
                solicitud.getMascota() == null ? null : solicitud.getMascota().getId(),
                solicitud.getEstado(),
                solicitud.getPorcentajeAfinidad(),
                solicitud.getDatosHogar(),
                solicitud.getCita(),
                solicitud.getNotasInternas(),
                solicitud.getFechaSolicitud(),
                solicitud.getFechaEnRevision(),
                solicitud.getFechaEntrevista(),
                solicitud.getFechaVisitaHogar(),
                solicitud.getFechaDecision(),
                solicitud.getEstadoActivo(),
                solicitud.getFechaCreacion(),
                solicitud.getFechaActualizacion());
    }

    public SolicitudAdopcionServiceImpl(
            SolicitudAdopcionRepository solicitudAdopcionRepository,
            UsuarioRepository usuarioRepository,
            MascotaRepository mascotaRepository) {
        this.solicitudAdopcionRepository = solicitudAdopcionRepository;
        this.usuarioRepository = usuarioRepository;
        this.mascotaRepository = mascotaRepository;
    }
}
