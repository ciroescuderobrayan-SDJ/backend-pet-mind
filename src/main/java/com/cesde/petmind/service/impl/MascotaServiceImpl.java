package com.cesde.petmind.service.impl;

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.Fundacion;
import com.cesde.petmind.model.entity.Mascota;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.EstadoPublicacion;
import com.cesde.petmind.repository.FundacionRepository;
import com.cesde.petmind.repository.MascotaRepository;
import com.cesde.petmind.service.MascotaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;
    private final FundacionRepository fundacionRepository;

    @Override
    public List<Mascota> listar() {
        return mascotaRepository.findByEstadoActivoTrue();
    }

    @Override
    public Mascota obtenerPorId(Long id) {
        Mascota mascota = buscarActivo(mascotaRepository, id, "una mascota");

        return mascota;
    }

    @Override
    public Mascota crear(Mascota mascota) {
        validarYAsignarFundacion(mascota);

        if (mascota.getEstadoPublicacion() == EstadoPublicacion.PUBLICADA
                && mascota.getFechaPublicacion() == null) {
            mascota.setFechaPublicacion(LocalDateTime.now());
        }

        return mascotaRepository.save(mascota);
    }

    @Override
    public Mascota actualizar(Long id, Mascota mascota) {
        Mascota existente = obtenerPorId(id);
        validarYAsignarFundacion(mascota);

        existente.setFundacion(mascota.getFundacion());
        existente.setNombre(mascota.getNombre());
        existente.setEspecie(mascota.getEspecie());
        existente.setRaza(mascota.getRaza());
        existente.setEdadMeses(mascota.getEdadMeses());
        existente.setTamano(mascota.getTamano());
        existente.setSexo(mascota.getSexo());
        existente.setCualidadesFisicas(mascota.getCualidadesFisicas());
        existente.setCiudad(mascota.getCiudad());
        existente.setDescripcion(mascota.getDescripcion());
        existente.setHistoria(mascota.getHistoria());
        existente.setEtiquetas(mascota.getEtiquetas());
        existente.setAfinidad(mascota.getAfinidad());
        existente.setEstadoAdopcion(mascota.getEstadoAdopcion());
        existente.setEstadoPublicacion(mascota.getEstadoPublicacion());
        existente.setEsterilizado(mascota.getEsterilizado());
        existente.setVacunasAlDia(mascota.getVacunasAlDia());
        existente.setDesparasitado(mascota.getDesparasitado());
        existente.setTieneMicrochip(mascota.getTieneMicrochip());
        existente.setAptoApadrinamiento(mascota.getAptoApadrinamiento());
        existente.setFechaPublicacion(mascota.getFechaPublicacion());
        existente.setContadorVisitas(mascota.getContadorVisitas());

        if (existente.getEstadoPublicacion() == EstadoPublicacion.PUBLICADA
                && existente.getFechaPublicacion() == null) {
            existente.setFechaPublicacion(LocalDateTime.now());
        }

        return mascotaRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Mascota mascota = obtenerPorId(id);
        mascota.setEstadoActivo(false);
        mascotaRepository.save(mascota);
    }

    @Override
    public List<Mascota> listarPorEstado(
            EstadoPublicacion estadoPublicacion,
            EstadoAdopcion estadoAdopcion
    ) {
        return mascotaRepository
                .findByEstadoPublicacionAndEstadoAdopcionAndEstadoActivoTrue(
                        estadoPublicacion,
                        estadoAdopcion
                );
    }

    @Override
    public List<Mascota> buscarPorEspecieYCiudad(Especie especie, String ciudad) {
        return mascotaRepository
                .findByEspecieAndCiudadIgnoreCaseAndEstadoActivoTrue(especie, ciudad);
    }

    @Override
    public List<Mascota> listarPorFundacion(Long fundacionId) {
        return mascotaRepository.findByFundacionIdAndEstadoActivoTrue(fundacionId);
    }

    private void validarYAsignarFundacion(Mascota mascota) {
        if (mascota.getFundacion() == null || mascota.getFundacion().getId() == null) {
            throw new ReglaDeNegocioException("La mascota debe indicar una fundacion");
        }

        Long fundacionId = mascota.getFundacion().getId();

        Fundacion fundacion = buscarActivo(fundacionRepository, fundacionId, "una fundacion activa");

        mascota.setFundacion(fundacion);
    }
}