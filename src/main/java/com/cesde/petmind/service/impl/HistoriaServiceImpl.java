package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cesde.petmind.dto.HistoriaRequest;
import com.cesde.petmind.dto.HistoriaResponse;
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
    public List<HistoriaResponse> listar() {
        return historiaRepository.findByEstadoActivoTrue()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public HistoriaResponse obtenerPorId(Long id) {
        Historia historia = buscarActivo(historiaRepository, id, "una historia");
        return convertirAResponse(historia);
    }

    @Override
    public HistoriaResponse crear(HistoriaRequest request) {
        Historia historia = convertirAEntidad(request);

        historia.setContadorLikes(0);
        historia.setEsDestacada(
                historia.getEsDestacada() == null
                        ? Boolean.FALSE
                        : historia.getEsDestacada());
        historia.setFechaPublicacion(LocalDateTime.now());

        return convertirAResponse(historiaRepository.save(historia));
    }

    @Override
    public HistoriaResponse actualizar(Long id, HistoriaRequest request) {
        Historia existente = buscarActivo(historiaRepository, id, "una historia");
        Historia historia = convertirAEntidad(request);

        existente.setFundacion(historia.getFundacion());
        existente.setMascota(historia.getMascota());
        existente.setTitulo(historia.getTitulo());
        existente.setResumen(historia.getResumen());
        existente.setContenido(historia.getContenido());
        existente.setTipo(historia.getTipo());

        if (historia.getEsDestacada() != null) {
            existente.setEsDestacada(historia.getEsDestacada());
        }

        return convertirAResponse(historiaRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        Historia historia = buscarActivo(historiaRepository, id, "una historia");
        historia.setEstadoActivo(false);
        historiaRepository.save(historia);
    }

    private Historia convertirAEntidad(HistoriaRequest request) {
        Fundacion fundacion = buscarActivo(
                fundacionRepository,
                request.fundacionId(),
                "una fundación activa");

        Historia historia = new Historia();
        historia.setFundacion(fundacion);
        historia.setTitulo(request.titulo());
        historia.setResumen(request.resumen());
        historia.setContenido(request.contenido());
        historia.setTipo(request.tipo());
        historia.setEsDestacada(request.esDestacada());

        if (request.mascotaId() != null) {
            Mascota mascota = buscarActivo(
                    mascotaRepository,
                    request.mascotaId(),
                    "una mascota activa");

            historia.setMascota(mascota);
        }

        return historia;
    }

    private HistoriaResponse convertirAResponse(Historia historia) {
        return new HistoriaResponse(
                historia.getTitulo(),
                historia.getResumen(),
                historia.getContenido(),
                historia.getTipo(),
                historia.getContadorLikes(),
                historia.getEsDestacada(),
                historia.getFechaPublicacion(),
                historia.getEstadoActivo(),
                historia.getFechaCreacion(),
                historia.getFechaActualizacion());
    }

    public HistoriaServiceImpl(
            HistoriaRepository historiaRepository,
            FundacionRepository fundacionRepository,
            MascotaRepository mascotaRepository) {
        this.historiaRepository = historiaRepository;
        this.fundacionRepository = fundacionRepository;
        this.mascotaRepository = mascotaRepository;
    }
}