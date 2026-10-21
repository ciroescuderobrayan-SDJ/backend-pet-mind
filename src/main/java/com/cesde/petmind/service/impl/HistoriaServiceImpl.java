package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.Fundacion;
import com.cesde.petmind.model.entity.Historia;
import com.cesde.petmind.model.entity.Mascota;
import com.cesde.petmind.repository.FundacionRepository;
import com.cesde.petmind.repository.HistoriaRepository;
import com.cesde.petmind.repository.MascotaRepository;
import com.cesde.petmind.service.HistoriaService;

@Service
public class HistoriaServiceImpl implements HistoriaService {
    private final HistoriaRepository historiaRepository;
    private final FundacionRepository fundacionRepository;
    private final MascotaRepository mascotaRepository;

    @Override
    public List<Historia> listar() {
        return historiaRepository.findByEstadoActivoTrue();
    }

    @Override
    public Historia obtenerPorId(Long id) {
        Historia historia = buscarActivo(historiaRepository, id, "una historia");
        return historia;
    }

    @Override
    public Historia crear(Historia historia) {
        validarYAsignarRelaciones(historia);
        if (historia.getFechaPublicacion() == null) {
            historia.setFechaPublicacion(LocalDateTime.now());
        }
        return historiaRepository.save(historia);
    }

    @Override
    public Historia actualizar(Long id, Historia historia) {
        Historia existente = obtenerPorId(id);
        validarYAsignarRelaciones(historia);
        existente.setFundacion(historia.getFundacion());
        existente.setMascota(historia.getMascota());
        existente.setTitulo(historia.getTitulo());
        existente.setResumen(historia.getResumen());
        existente.setContenido(historia.getContenido());
        existente.setTipo(historia.getTipo());
        existente.setContadorLikes(historia.getContadorLikes());
        existente.setEsDestacada(historia.getEsDestacada());
        existente.setFechaPublicacion(historia.getFechaPublicacion());
        return historiaRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Historia historia = obtenerPorId(id);
        historia.setEstadoActivo(false);
        historiaRepository.save(historia);
    }

    private void validarYAsignarRelaciones(Historia historia) {
        if (historia.getFundacion() == null || historia.getFundacion().getId() == null) {
            throw new ReglaDeNegocioException("La historia debe indicar una fundacion");
        }
        Long fundacionId = historia.getFundacion().getId();
        Fundacion fundacion = buscarActivo(fundacionRepository, fundacionId, "una fundacion activa");
        historia.setFundacion(fundacion);
        if (historia.getMascota() != null) {
            if (historia.getMascota().getId() == null) {
                throw new ReglaDeNegocioException("La mascota de la historia debe tener un id");
            }
            Long mascotaId = historia.getMascota().getId();
            Mascota mascota = buscarActivo(mascotaRepository, mascotaId, "una mascota activa");
            historia.setMascota(mascota);
        }
    }
    public HistoriaServiceImpl(final HistoriaRepository historiaRepository, final FundacionRepository fundacionRepository, final MascotaRepository mascotaRepository) {
        this.historiaRepository = historiaRepository;
        this.fundacionRepository = fundacionRepository;
        this.mascotaRepository = mascotaRepository;
    }
}
