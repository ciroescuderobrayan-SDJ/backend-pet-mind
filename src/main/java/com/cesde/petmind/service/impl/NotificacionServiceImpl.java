package com.cesde.petmind.service.impl;

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.Notificacion;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.repository.NotificacionRepository;
import com.cesde.petmind.repository.UsuarioRepository;
import com.cesde.petmind.service.NotificacionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;

    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Notificacion> listar() {
        return notificacionRepository.findByEstadoActivoTrue();
    }

    @Override
    public Notificacion obtenerPorId(Long id) {
        // Una notificacion borrada logicamente se trata igual que una que no existe
        Notificacion notificacion = buscarActivo(notificacionRepository, id, "una notificacion");
        return notificacion;
    }

    @Override
    public Notificacion crear(Notificacion notificacion) {
        if (notificacion.getUsuario() == null) {
            throw new ReglaDeNegocioException("La notificacion debe indicar el usuario que la recibe");
        }

        // En el JSON solo llega el id; aca se trae el usuario completo de la base
        Long usuarioId = notificacion.getUsuario().getId();
        Usuario usuario = buscarActivo(usuarioRepository, usuarioId, "un usuario");
        notificacion.setUsuario(usuario);

        return notificacionRepository.save(notificacion);
    }

    @Override
    public Notificacion actualizar(Long id, Notificacion notificacion) {
        Notificacion existente = obtenerPorId(id);

        // El usuario que la recibe no cambia
        existente.setTipo(notificacion.getTipo());
        existente.setTitulo(notificacion.getTitulo());
        existente.setMensaje(notificacion.getMensaje());
        existente.setUrlDestino(notificacion.getUrlDestino());
        existente.setLeida(notificacion.getLeida());
        return notificacionRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Notificacion notificacion = obtenerPorId(id);

        // Borrado logico: no se borra la fila, solo se desactiva
        notificacion.setEstadoActivo(false);
        notificacionRepository.save(notificacion);
    }

    @Override
    public List<Notificacion> listarNoLeidasPorUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdAndLeidaFalseAndEstadoActivoTrue(usuarioId);
    }

    @Override
    public Notificacion marcarComoLeida(Long id) {
        Notificacion notificacion = obtenerPorId(id);
        notificacion.setLeida(true);
        return notificacionRepository.save(notificacion);
    }
}
