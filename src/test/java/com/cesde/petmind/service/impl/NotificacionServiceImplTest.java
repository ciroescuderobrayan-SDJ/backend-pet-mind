package com.cesde.petmind.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.cesde.petmind.dto.NotificacionRequest;
import com.cesde.petmind.model.entity.Notificacion;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.model.enums.TipoNotificacion;
import com.cesde.petmind.repository.NotificacionRepository;
import com.cesde.petmind.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceImplTest {
    @Mock NotificacionRepository notificaciones;
    @Mock UsuarioRepository usuarios;
    @InjectMocks NotificacionServiceImpl servicio;

    @Test
    void crearResuelveUsuarioYDevuelveIds() {
        Usuario usuario = new Usuario(); usuario.setId(4L);
        when(usuarios.findById(4L)).thenReturn(Optional.of(usuario));
        when(notificaciones.save(any(Notificacion.class))).thenAnswer(inv -> {
            Notificacion n = inv.getArgument(0); n.setId(5L); return n;
        });
        var resultado = servicio.crear(new NotificacionRequest(4L, TipoNotificacion.SISTEMA, "Título", "Mensaje", null, false));
        assertEquals(5L, resultado.id()); assertEquals(4L, resultado.usuarioId()); assertFalse(resultado.leida());
    }

    @Test
    void marcarComoLeidaYListarConservanIdDeUsuario() {
        Usuario usuario = new Usuario(); usuario.setId(4L);
        Notificacion n = new Notificacion(); n.setId(5L); n.setUsuario(usuario);
        when(notificaciones.findById(5L)).thenReturn(Optional.of(n));
        when(notificaciones.save(n)).thenReturn(n);
        when(notificaciones.findByUsuarioIdAndLeidaFalseAndEstadoActivoTrue(4L)).thenReturn(List.of(n));
        assertEquals(4L, servicio.listarNoLeidasPorUsuario(4L).get(0).usuarioId());
        assertTrue(servicio.marcarComoLeida(5L).leida());
    }
}
