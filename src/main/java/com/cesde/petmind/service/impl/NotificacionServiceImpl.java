package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cesde.petmind.dto.NotificacionRequest;
import com.cesde.petmind.dto.NotificacionResponse;
import com.cesde.petmind.model.entity.Notificacion;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.repository.NotificacionRepository;
import com.cesde.petmind.repository.UsuarioRepository;
import com.cesde.petmind.service.NotificacionService;

@Service
@Transactional
public class NotificacionServiceImpl implements NotificacionService {
    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<NotificacionResponse> listar() {
        return notificacionRepository.findByEstadoActivoTrue().stream().map(this::aResponse).toList();
    }

    @Override
    public NotificacionResponse obtenerPorId(Long id) {
        // Una notificacion borrada logicamente se trata igual que una que no existe
        return aResponse(buscarNotificacion(id));
    }

    @Override
    public NotificacionResponse crear(NotificacionRequest request) {
        Usuario usuario = buscarActivo(usuarioRepository, request.usuarioId(), "un usuario");
        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(usuario);
        aplicarDatos(notificacion, request);
        return aResponse(notificacionRepository.save(notificacion));
    }

    @Override
    public NotificacionResponse actualizar(Long id, NotificacionRequest request) {
        Notificacion existente = buscarNotificacion(id);
        // El usuario que la recibe no cambia
        aplicarDatos(existente, request);
        return aResponse(notificacionRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        Notificacion notificacion = buscarNotificacion(id);
        // Borrado logico: no se borra la fila, solo se desactiva
        notificacion.setEstadoActivo(false);
        notificacionRepository.save(notificacion);
    }

    @Override
    public List<NotificacionResponse> listarNoLeidasPorUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdAndLeidaFalseAndEstadoActivoTrue(usuarioId).stream().map(this::aResponse).toList();
    }

    @Override
    public NotificacionResponse marcarComoLeida(Long id) {
        Notificacion notificacion = buscarNotificacion(id);
        notificacion.setLeida(true);
        return aResponse(notificacionRepository.save(notificacion));
    }

    private Notificacion buscarNotificacion(Long id) {
        return buscarActivo(notificacionRepository, id, "una notificacion");
    }

    private void aplicarDatos(Notificacion notificacion, NotificacionRequest request) {
        notificacion.setTipo(request.tipo());
        notificacion.setTitulo(request.titulo());
        notificacion.setMensaje(request.mensaje());
        notificacion.setUrlDestino(request.urlDestino());
        notificacion.setLeida(request.leida());
    }

    private NotificacionResponse aResponse(Notificacion notificacion) {
        return new NotificacionResponse(notificacion.getId(), notificacion.getUsuario() == null ? null : notificacion.getUsuario().getId(),
                notificacion.getTipo(), notificacion.getTitulo(), notificacion.getMensaje(), notificacion.getUrlDestino(),
                notificacion.getLeida(), notificacion.getEstadoActivo(), notificacion.getFechaCreacion(), notificacion.getFechaActualizacion());
    }
    public NotificacionServiceImpl(final NotificacionRepository notificacionRepository, final UsuarioRepository usuarioRepository) {
        this.notificacionRepository = notificacionRepository;
        this.usuarioRepository = usuarioRepository;
    }
}
