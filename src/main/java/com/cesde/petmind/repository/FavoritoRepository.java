package com.cesde.petmind.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cesde.petmind.model.entity.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    // Borrado logico: los listados solo traen los registros activos
    List<Favorito> findByEstadoActivoTrue();

    // Favoritos guardados por un usuario
    List<Favorito> findByUsuarioIdAndEstadoActivoTrue(Long usuarioId);

    // Regla de favorito unico: un usuario guarda cada mascota, campana o fundacion una sola vez
    boolean existsByUsuarioIdAndMascotaIdAndEstadoActivoTrue(Long usuarioId, Long mascotaId);

    boolean existsByUsuarioIdAndCampanaIdAndEstadoActivoTrue(Long usuarioId, Long campanaId);

    boolean existsByUsuarioIdAndFundacionIdAndEstadoActivoTrue(Long usuarioId, Long fundacionId);
}
