package com.cesde.petmind.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.cesde.petmind.dto.ContactoDto;
import com.cesde.petmind.dto.FundacionRequest;
import com.cesde.petmind.dto.FundacionResponse;
import com.cesde.petmind.exceptions.RecursoNoEncontradoException;
import com.cesde.petmind.model.entity.Fundacion;
import com.cesde.petmind.model.enums.EstadoVerificacion;
import com.cesde.petmind.repository.FundacionRepository;

@ExtendWith(MockitoExtension.class)
class FundacionServiceImplTest {
    @Mock private FundacionRepository repositorio;
    @InjectMocks private FundacionServiceImpl servicio;

    private FundacionRequest request() {
        return new FundacionRequest("Huellas", "123", "Ana", "Ayuda animal", null,
                new ContactoDto("123", null, "contacto@huellas.co"), null, null, null, null,
                EstadoVerificacion.PENDIENTE, null, null, null);
    }

    @Test
    void crearDevuelveIdYDatosGuardados() {
        when(repositorio.save(any(Fundacion.class))).thenAnswer(invocacion -> {
            Fundacion fundacion = invocacion.getArgument(0);
            fundacion.setId(5L);
            return fundacion;
        });
        FundacionResponse respuesta = servicio.crear(request());
        assertEquals(5L, respuesta.id());
        assertEquals("Huellas", respuesta.nombre());
        assertEquals("contacto@huellas.co", respuesta.contacto().emailContacto());
    }

    @Test
    void actualizarModificaLaFundacionExistente() {
        Fundacion existente = new Fundacion();
        existente.setId(5L);
        when(repositorio.findById(5L)).thenReturn(Optional.of(existente));
        when(repositorio.save(existente)).thenReturn(existente);
        FundacionResponse respuesta = servicio.actualizar(5L, request());
        assertEquals(5L, respuesta.id());
        assertEquals("123", existente.getNit());
    }

    @Test
    void eliminarEsLogicoYNoPermiteConsultarInactivos() {
        Fundacion existente = new Fundacion();
        existente.setId(5L);
        when(repositorio.findById(5L)).thenReturn(Optional.of(existente));
        servicio.eliminar(5L);
        assertFalse(existente.getEstadoActivo());
        verify(repositorio).save(existente);
        assertThrows(RecursoNoEncontradoException.class, () -> servicio.obtenerPorId(5L));
    }
}
