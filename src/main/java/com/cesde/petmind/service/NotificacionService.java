package com.cesde.petmind.service;

import java.util.List;
import com.cesde.petmind.dto.NotificacionRequest;
import com.cesde.petmind.dto.NotificacionResponse;

public interface NotificacionService {
    
    List<NotificacionResponse> listar();
    NotificacionResponse obtenerPorId(Long id);
    NotificacionResponse crear(NotificacionRequest notificacion);
    NotificacionResponse actualizar(Long id, NotificacionRequest notificacion);
    void eliminar(Long id);

    List<NotificacionResponse> listarNoLeidasPorUsuario(Long usuarioId);

    NotificacionResponse marcarComoLeida(Long id);
    
}
