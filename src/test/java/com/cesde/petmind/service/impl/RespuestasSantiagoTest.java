package com.cesde.petmind.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import com.cesde.petmind.model.entity.*;
import com.cesde.petmind.repository.*;

class RespuestasSantiagoTest {
    @Test
    void mascotaYSolicitudDevuelvenIdsDeSusRelaciones() {
        Fundacion fundacion = new Fundacion(); fundacion.setId(2L);
        Mascota mascota = new Mascota(); mascota.setId(3L); mascota.setFundacion(fundacion);
        Usuario usuario = new Usuario(); usuario.setId(4L);
        SolicitudAdopcion solicitud = new SolicitudAdopcion(); solicitud.setId(5L);
        solicitud.setMascota(mascota); solicitud.setUsuario(usuario);
        MascotaRepository mascotas = mock(MascotaRepository.class);
        SolicitudAdopcionRepository solicitudes = mock(SolicitudAdopcionRepository.class);
        when(mascotas.findById(3L)).thenReturn(Optional.of(mascota));
        when(solicitudes.findById(5L)).thenReturn(Optional.of(solicitud));
        var m = new MascotaServiceImpl(mascotas, mock(FundacionRepository.class)).obtenerPorId(3L);
        var s = new SolicitudAdopcionServiceImpl(solicitudes, mock(UsuarioRepository.class), mascotas).obtenerPorId(5L);
        assertEquals(3L, m.id()); assertEquals(2L, m.fundacionId());
        assertEquals(5L, s.id()); assertEquals(4L, s.usuarioId()); assertEquals(3L, s.mascotaId());
    }

    @Test
    void historiasActualizacionesYReportesIncluyenRelacionesOpcionales() {
        Fundacion fundacion = new Fundacion(); fundacion.setId(2L);
        Historia historia = new Historia(); historia.setId(6L); historia.setFundacion(fundacion);
        Actualizacion actualizacion = new Actualizacion(); actualizacion.setId(7L); actualizacion.setHistoria(historia);
        ReporteAnimal reporte = new ReporteAnimal(); reporte.setId(8L);
        ReporteFundacion asignacion = new ReporteFundacion(); asignacion.setId(9L);
        asignacion.setFundacion(fundacion); asignacion.setReporte(reporte);
        HistoriaRepository historias = mock(HistoriaRepository.class);
        ActualizacionRepository actualizaciones = mock(ActualizacionRepository.class);
        ReporteFundacionRepository reportes = mock(ReporteFundacionRepository.class);
        when(historias.findById(6L)).thenReturn(Optional.of(historia));
        when(actualizaciones.findById(7L)).thenReturn(Optional.of(actualizacion));
        when(reportes.findById(9L)).thenReturn(Optional.of(asignacion));
        var h = new HistoriaServiceImpl(historias, mock(FundacionRepository.class), mock(MascotaRepository.class)).obtenerPorId(6L);
        var a = new ActualizacionServiceImpl(actualizaciones, mock(CampanaDonacionRepository.class), historias).obtenerPorId(7L);
        var r = new ReporteFundacionServiceImpl(reportes, mock(ReporteAnimalRepository.class), mock(FundacionRepository.class)).obtenerPorId(9L);
        assertEquals(6L, h.id()); assertEquals(2L, h.fundacionId()); assertNull(h.mascotaId());
        assertEquals(7L, a.id()); assertEquals(6L, a.historiaId()); assertNull(a.campanaId());
        assertEquals(9L, r.id()); assertEquals(8L, r.reporteId()); assertEquals(2L, r.fundacionId());
    }
}
