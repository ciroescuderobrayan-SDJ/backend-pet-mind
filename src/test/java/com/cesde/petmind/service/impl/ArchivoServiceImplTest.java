package com.cesde.petmind.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.cesde.petmind.dto.ArchivoRequest;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.*;
import com.cesde.petmind.model.enums.*;
import com.cesde.petmind.repository.*;

@ExtendWith(MockitoExtension.class)
class ArchivoServiceImplTest {
    @Mock ArchivoRepository archivos;
    @Mock MascotaRepository mascotas;
    @Mock SolicitudAdopcionRepository solicitudes;
    @Mock CampanaDonacionRepository campanas;
    @Mock HistoriaRepository historias;
    @Mock ReporteAnimalRepository reportes;
    @InjectMocks ArchivoServiceImpl servicio;

    private ArchivoRequest request(Long mascotaId, Long campanaId) {
        return new ArchivoRequest(mascotaId, null, campanaId, null, null, "https://ejemplo.com/foto",
                TipoMedio.IMAGEN, CategoriaArchivo.GALERIA, false, 0);
    }

    @Test
    void exigeUnSoloDueno() {
        assertThrows(ReglaDeNegocioException.class, () -> servicio.crear(request(null, null)));
        assertThrows(ReglaDeNegocioException.class, () -> servicio.crear(request(1L, 2L)));
        verify(archivos, never()).save(any());
    }

    @Test
    void crearResuelveDuenoYDevuelveIds() {
        Mascota mascota = new Mascota(); mascota.setId(2L);
        when(mascotas.findById(2L)).thenReturn(Optional.of(mascota));
        when(archivos.save(any(Archivo.class))).thenAnswer(inv -> { Archivo a = inv.getArgument(0); a.setId(3L); return a; });
        var respuesta = servicio.crear(request(2L, null));
        assertEquals(3L, respuesta.id()); assertEquals(2L, respuesta.mascotaId()); assertNull(respuesta.campanaId());
    }

    @Test
    void actualizarConservaDueno() {
        Mascota mascota = new Mascota(); mascota.setId(2L);
        Archivo archivo = new Archivo(); archivo.setId(3L); archivo.setMascota(mascota);
        when(archivos.findById(3L)).thenReturn(Optional.of(archivo));
        when(archivos.save(archivo)).thenReturn(archivo);
        var respuesta = servicio.actualizar(3L, request(null, 8L));
        assertEquals(2L, respuesta.mascotaId()); assertNull(respuesta.campanaId());
    }
}
