package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cesde.petmind.dto.CambioEstadoSolicitudRequest;
import com.cesde.petmind.dto.RespuestaSolicitudRequest;
import com.cesde.petmind.dto.SolicitanteDto;
import com.cesde.petmind.dto.SolicitudAdopcionRequest;
import com.cesde.petmind.dto.SolicitudAdopcionResponse;
import com.cesde.petmind.exceptions.AccesoDenegadoException;
import com.cesde.petmind.exceptions.RecursoDuplicadoException;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.Mascota;
import com.cesde.petmind.model.entity.SolicitudAdopcion;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.EstadoSolicitud;
import com.cesde.petmind.model.enums.RolUsuario;
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
        validarPuedeSolicitar(solicitud.getUsuario(), solicitud.getMascota());
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

    @Override
    public List<SolicitudAdopcionResponse> listarPorFundacion(Long fundacionId) {
        return solicitudAdopcionRepository.findByMascotaFundacionIdAndEstadoActivoTrue(fundacionId)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    // La fundacion revisa, pide documentos, aprueba o rechaza.
    @Override
    public SolicitudAdopcionResponse cambiarEstado(Long id, CambioEstadoSolicitudRequest request) {
        SolicitudAdopcion solicitud = buscarActivo(solicitudAdopcionRepository, id, "una solicitud de adopción");
        Usuario responsable = buscarActivo(usuarioRepository, request.usuarioId(), "un usuario");
        validarPuedeDecidir(responsable, solicitud);

        EstadoSolicitud actual = solicitud.getEstado();
        EstadoSolicitud destino = request.estado();
        if (actual != null && actual.estaCerrada()) {
            throw new ReglaDeNegocioException("La solicitud ya está cerrada (" + actual + ") y no se puede cambiar");
        }
        if (!EstadoSolicitud.DECISIONES_FUNDACION.contains(destino)) {
            throw new ReglaDeNegocioException("La fundación no puede pasar una solicitud a " + destino);
        }
        String mensaje = textoLimpio(request.mensaje());
        if (destino == EstadoSolicitud.DOCUMENTOS_PENDIENTES && mensaje == null) {
            throw new ReglaDeNegocioException("Escribe qué documentos necesitas de la persona");
        }
        if (destino == EstadoSolicitud.RECHAZADA && mensaje == null) {
            throw new ReglaDeNegocioException("Escribe el motivo del rechazo");
        }

        LocalDateTime ahora = LocalDateTime.now();
        if (destino == EstadoSolicitud.APROBADA) {
            aprobarAdopcion(solicitud, ahora);
        }
        solicitud.setEstado(destino);
        // El mensaje anterior ya no aplica: cada decision deja solo el suyo
        solicitud.setMensajeFundacion(mensaje);
        if (destino == EstadoSolicitud.DOCUMENTOS_PENDIENTES) {
            solicitud.setRespuestaAdoptante(null);
        }
        if (solicitud.getFechaEnRevision() == null) {
            solicitud.setFechaEnRevision(ahora);
        }
        if (destino == EstadoSolicitud.ENTREVISTA) {
            solicitud.setFechaEntrevista(ahora);
        }
        if (destino == EstadoSolicitud.VISITA_HOGAR) {
            solicitud.setFechaVisitaHogar(ahora);
        }
        if (destino.estaCerrada()) {
            solicitud.setFechaDecision(ahora);
        }
        return convertirAResponse(solicitudAdopcionRepository.save(solicitud));
    }

    // La persona envia lo que la fundacion le pidio; la solicitud vuelve a revision.
    @Override
    public SolicitudAdopcionResponse responder(Long id, RespuestaSolicitudRequest request) {
        SolicitudAdopcion solicitud = buscarActivo(solicitudAdopcionRepository, id, "una solicitud de adopción");
        Long duenoId = solicitud.getUsuario() == null ? null : solicitud.getUsuario().getId();
        if (!Objects.equals(duenoId, request.usuarioId())) {
            throw new AccesoDenegadoException("Solo la persona que envió la solicitud puede responderla");
        }
        if (solicitud.getEstado() != EstadoSolicitud.DOCUMENTOS_PENDIENTES) {
            throw new ReglaDeNegocioException("La fundación no ha pedido documentos para esta solicitud");
        }
        solicitud.setRespuestaAdoptante(request.respuesta().trim());
        solicitud.setEstado(EstadoSolicitud.EN_REVISION);
        return convertirAResponse(solicitudAdopcionRepository.save(solicitud));
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

    // Solo las personas adoptan, y cada una tiene como maximo una solicitud abierta por mascota
    private void validarPuedeSolicitar(Usuario usuario, Mascota mascota) {
        if (usuario.getRol() != RolUsuario.ADOPTANTE) {
            throw new ReglaDeNegocioException("Solo las cuentas de personas pueden enviar solicitudes de adopción");
        }
        if (solicitudAdopcionRepository.existsByUsuarioIdAndMascotaIdAndEstadoInAndEstadoActivoTrue(
                usuario.getId(), mascota.getId(), EstadoSolicitud.ABIERTOS)) {
            throw new RecursoDuplicadoException("Ya tienes una solicitud en curso para " + mascota.getNombre());
        }
    }

    // Decide el administrador o un representante de la fundacion que publico la mascota
    private void validarPuedeDecidir(Usuario responsable, SolicitudAdopcion solicitud) {
        if (responsable.getRol() == RolUsuario.ADMIN) {
            return;
        }
        Long fundacionDeLaMascota = solicitud.getMascota() == null || solicitud.getMascota().getFundacion() == null
                ? null
                : solicitud.getMascota().getFundacion().getId();
        Long fundacionDelResponsable = responsable.getFundacion() == null ? null : responsable.getFundacion().getId();
        if (responsable.getRol() != RolUsuario.REPRESENTANTE_FUNDACION
                || fundacionDeLaMascota == null
                || !fundacionDeLaMascota.equals(fundacionDelResponsable)) {
            throw new AccesoDenegadoException(
                    "Solo la fundación que publicó la mascota puede decidir esta solicitud");
        }
    }

    // Aprobar: la mascota queda adoptada y las demas solicitudes abiertas se cierran
    private void aprobarAdopcion(SolicitudAdopcion aprobada, LocalDateTime ahora) {
        Mascota mascota = aprobada.getMascota();
        if (mascota.getEstadoAdopcion() == EstadoAdopcion.ADOPTADO) {
            throw new ReglaDeNegocioException("La mascota ya fue adoptada");
        }
        mascota.setEstadoAdopcion(EstadoAdopcion.ADOPTADO);
        mascotaRepository.save(mascota);

        List<SolicitudAdopcion> otras = solicitudAdopcionRepository
                .findByMascotaIdAndEstadoInAndEstadoActivoTrue(mascota.getId(), EstadoSolicitud.ABIERTOS)
                .stream()
                .filter(otra -> !Objects.equals(otra.getId(), aprobada.getId()))
                .toList();
        for (SolicitudAdopcion otra : otras) {
            otra.setEstado(EstadoSolicitud.RECHAZADA);
            otra.setMensajeFundacion(mascota.getNombre()
                    + " ya encontró hogar con otra familia. Gracias por tu interés en adoptar.");
            otra.setFechaDecision(ahora);
        }
        if (!otras.isEmpty()) {
            solicitudAdopcionRepository.saveAll(otras);
        }
    }

    private static String textoLimpio(String texto) {
        return texto == null || texto.isBlank() ? null : texto.trim();
    }

    private SolicitudAdopcionResponse convertirAResponse(
            SolicitudAdopcion solicitud) {
        Mascota mascota = solicitud.getMascota();
        Usuario usuario = solicitud.getUsuario();
        return new SolicitudAdopcionResponse(
                solicitud.getId(),
                usuario == null ? null : usuario.getId(),
                mascota == null ? null : mascota.getId(),
                mascota == null || mascota.getFundacion() == null ? null : mascota.getFundacion().getId(),
                mascota == null ? null : mascota.getNombre(),
                solicitante(usuario),
                solicitud.getEstado(),
                solicitud.getPorcentajeAfinidad(),
                solicitud.getDatosHogar(),
                solicitud.getCita(),
                solicitud.getNotasInternas(),
                solicitud.getMensajeFundacion(),
                solicitud.getRespuestaAdoptante(),
                solicitud.getFechaSolicitud(),
                solicitud.getFechaEnRevision(),
                solicitud.getFechaEntrevista(),
                solicitud.getFechaVisitaHogar(),
                solicitud.getFechaDecision(),
                solicitud.getEstadoActivo(),
                solicitud.getFechaCreacion(),
                solicitud.getFechaActualizacion());
    }

    private static SolicitanteDto solicitante(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new SolicitanteDto(
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getContacto() == null ? null : usuario.getContacto().getEmailContacto(),
                usuario.getContacto() == null ? null : usuario.getContacto().getTelefonoPrincipal(),
                usuario.getDireccion() == null ? null : usuario.getDireccion().getCiudad());
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
