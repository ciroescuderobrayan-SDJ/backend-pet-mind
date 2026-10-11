package com.cesde.petmind.service.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.cesde.petmind.exceptions.RecursoNoEncontradoException;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.Mascota;
import com.cesde.petmind.repository.MascotaRepository;

class BuscadorEntidadesTest {

    private final MascotaRepository repositorio = mock(MascotaRepository.class);

    @Test
    void devuelveElRegistroActivo() {
        Mascota luna = Mascota.builder().id(1L).build();
        when(repositorio.findById(1L)).thenReturn(Optional.of(luna));

        assertSame(luna, BuscadorEntidades.buscarActivo(repositorio, 1L, "una mascota"));
    }

    @Test
    void unRegistroConBorradoLogicoCuentaComoInexistente() {
        Mascota borrada = Mascota.builder().id(2L).estadoActivo(false).build();
        when(repositorio.findById(2L)).thenReturn(Optional.of(borrada));

        RecursoNoEncontradoException error = assertThrows(RecursoNoEncontradoException.class,
                () -> BuscadorEntidades.buscarActivo(repositorio, 2L, "una mascota"));
        assertEquals("No existe una mascota con id 2", error.getMessage());
    }

    @Test
    void unIdNuloEsUnErrorDelCliente() {
        ReglaDeNegocioException error = assertThrows(ReglaDeNegocioException.class,
                () -> BuscadorEntidades.buscarActivo(repositorio, null, "una mascota"));
        assertEquals("Falta el id de una mascota", error.getMessage());
    }
}
