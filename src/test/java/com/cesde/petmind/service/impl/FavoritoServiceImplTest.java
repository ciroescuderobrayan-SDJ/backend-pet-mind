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

    private Favorito favoritoDeMascota(Long usuarioId, Long mascotaId) {
        return Favorito.builder()
                .usuario(Usuario.builder().id(usuarioId).build())
                .mascota(Mascota.builder().id(mascotaId).build())
                .build();
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
}
