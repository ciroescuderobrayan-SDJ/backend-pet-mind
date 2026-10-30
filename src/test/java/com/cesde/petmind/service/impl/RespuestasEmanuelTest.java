package com.cesde.petmind.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import com.cesde.petmind.model.entity.*;
import com.cesde.petmind.repository.*;

class RespuestasEmanuelTest {
    @Test
    void campanaDonacionYGastoIncluyenIdentificadores() {
        Fundacion fundacion = new Fundacion(); fundacion.setId(1L);
        CampanaDonacion campana = new CampanaDonacion(); campana.setId(2L); campana.setFundacion(fundacion);
        Donacion origen = new Donacion(); origen.setId(3L);
        Donacion donacion = new Donacion(); donacion.setId(4L); donacion.setCampana(campana); donacion.setDonacionOrigen(origen);
        GastoCampana gasto = new GastoCampana(); gasto.setId(5L); gasto.setCampana(campana);
        CampanaDonacionRepository campanas = mock(CampanaDonacionRepository.class);
        DonacionRepository donaciones = mock(DonacionRepository.class);
        GastoCampanaRepository gastos = mock(GastoCampanaRepository.class);
        when(campanas.findById(2L)).thenReturn(Optional.of(campana));
        when(donaciones.findById(4L)).thenReturn(Optional.of(donacion));
        when(gastos.findById(5L)).thenReturn(Optional.of(gasto));
        var c = new CampanaDonacionServiceImpl(campanas, mock(FundacionRepository.class), mock(MascotaRepository.class)).obtenerPorId(2L);
        var d = new DonacionServiceImpl(donaciones, mock(UsuarioRepository.class), campanas).obtenerPorId(4L);
        var g = new GastoCampanaServiceImpl(gastos, campanas).obtenerPorId(5L);
        assertEquals(2L, c.id()); assertEquals(1L, c.fundacionId()); assertNull(c.mascotaId());
        assertEquals(4L, d.id()); assertEquals(2L, d.campanaId()); assertEquals(3L, d.donacionOrigenId()); assertNull(d.usuarioId());
        assertEquals(5L, g.id()); assertEquals(2L, g.campanaId());
    }

    @Test
    void reporteIncluyeUsuarioCuandoExiste() {
        Usuario usuario = new Usuario(); usuario.setId(8L);
        ReporteAnimal reporte = new ReporteAnimal(); reporte.setId(9L); reporte.setUsuario(usuario);
        ReporteAnimalRepository reportes = mock(ReporteAnimalRepository.class);
        when(reportes.findById(9L)).thenReturn(Optional.of(reporte));
        var respuesta = new ReporteAnimalServiceImpl(reportes, mock(UsuarioRepository.class)).obtenerPorId(9L);
        assertEquals(9L, respuesta.id()); assertEquals(8L, respuesta.usuarioId());
    }
}
