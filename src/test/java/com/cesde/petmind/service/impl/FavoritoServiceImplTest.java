package com.cesde.petmind.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cesde.petmind.exceptions.RecursoDuplicadoException;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.dto.FavoritoRequest;
import com.cesde.petmind.model.entity.Favorito;
import com.cesde.petmind.model.entity.Mascota;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.repository.CampanaDonacionRepository;
import com.cesde.petmind.repository.FavoritoRepository;
import com.cesde.petmind.repository.FundacionRepository;
import com.cesde.petmind.repository.MascotaRepository;
import com.cesde.petmind.repository.UsuarioRepository;

// Pruebas de las reglas de favoritos: un solo destino, sin repetidos y sin ids vacios.
@ExtendWith(MockitoExtension.class)
class FavoritoServiceImplTest {

    @Mock
    private FavoritoRepository favoritoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private MascotaRepository mascotaRepository;
    @Mock
    private CampanaDonacionRepository campanaDonacionRepository;
    @Mock
    private FundacionRepository fundacionRepository;

    @InjectMocks
    private FavoritoServiceImpl favoritoService;

    private FavoritoRequest favoritoDeMascota(Long usuarioId, Long mascotaId) {
        return new FavoritoRequest(usuarioId, mascotaId, null, null);
    }

    @Test
    void crearRechazaUnFavoritoRepetido() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(Usuario.builder().id(1L).build()));
        when(mascotaRepository.findById(5L)).thenReturn(Optional.of(Mascota.builder().id(5L).build()));
        when(favoritoRepository.existsByUsuarioIdAndMascotaIdAndEstadoActivoTrue(1L, 5L)).thenReturn(true);

        assertThrows(RecursoDuplicadoException.class, () -> favoritoService.crear(favoritoDeMascota(1L, 5L)));
        verify(favoritoRepository, never()).save(any(Favorito.class));
    }

    @Test
    void crearConUsuarioSinIdResponde400YNo500() {
        // Antes findById(null) lanzaba IllegalArgumentException y la API respondia 500
        assertThrows(ReglaDeNegocioException.class, () -> favoritoService.crear(favoritoDeMascota(null, 5L)));
    }

    @Test
    void crearRechazaVariosDestinos() {
        assertThrows(ReglaDeNegocioException.class, () -> favoritoService.crear(new FavoritoRequest(1L, 2L, 3L, null)));
        verify(favoritoRepository, never()).save(any(Favorito.class));
    }

    @Test
    void crearDevuelveIdsYNoLaEntidad() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(Usuario.builder().id(1L).build()));
        when(mascotaRepository.findById(5L)).thenReturn(Optional.of(Mascota.builder().id(5L).build()));
        when(favoritoRepository.save(any(Favorito.class))).thenAnswer(inv -> {
            Favorito favorito = inv.getArgument(0); favorito.setId(7L); return favorito;
        });
        var respuesta = favoritoService.crear(favoritoDeMascota(1L, 5L));
        org.junit.jupiter.api.Assertions.assertEquals(7L, respuesta.id());
        org.junit.jupiter.api.Assertions.assertEquals(1L, respuesta.usuarioId());
        org.junit.jupiter.api.Assertions.assertEquals(5L, respuesta.mascotaId());
    }
}
