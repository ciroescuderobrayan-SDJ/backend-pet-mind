package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cesde.petmind.dto.CampanaDonacionRequest;
import com.cesde.petmind.dto.CampanaDonacionResponse;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.CampanaDonacion;
import com.cesde.petmind.model.entity.Fundacion;
import com.cesde.petmind.model.entity.Mascota;
import com.cesde.petmind.model.enums.EstadoCampana;
import com.cesde.petmind.repository.CampanaDonacionRepository;
import com.cesde.petmind.repository.FundacionRepository;
import com.cesde.petmind.repository.MascotaRepository;
import com.cesde.petmind.service.CampanaDonacionService;

@Service
@Transactional
public class CampanaDonacionServiceImpl implements CampanaDonacionService {
    private final CampanaDonacionRepository campanaDonacionRepository;
    private final FundacionRepository fundacionRepository;
    private final MascotaRepository mascotaRepository;

    @Override
    public List<CampanaDonacionResponse> listar() {
        return campanaDonacionRepository.findByEstadoActivoTrue().stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public List<CampanaDonacionResponse> listarPorEstado(EstadoCampana estado) {
        if (estado == null) {
            throw new ReglaDeNegocioException("El estado de la campana es obligatorio");
        }
        return campanaDonacionRepository.findByEstadoAndEstadoActivoTrue(estado).stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public CampanaDonacionResponse obtenerPorId(Long id) {
        CampanaDonacion campana = buscarActivo(campanaDonacionRepository, id, "una campana");
        return convertirAResponse(campana);
    }

    @Override
    public CampanaDonacionResponse crear(CampanaDonacionRequest request) {
        CampanaDonacion campana = convertirAEntidad(request);
        validarDatosCampana(campana);
        resolverRelaciones(campana);
        return convertirAResponse(campanaDonacionRepository.save(campana));
    }

    @Override
    public CampanaDonacionResponse actualizar(Long id, CampanaDonacionRequest request) {
        CampanaDonacion campana = convertirAEntidad(request);
        validarDatosCampana(campana);
        CampanaDonacion existente = buscarActivo(campanaDonacionRepository, id, "una campana");
        if (campana.getFundacion() != null
                && !campana.getFundacion().getId().equals(existente.getFundacion().getId())) {
            throw new ReglaDeNegocioException("La fundacion de una campana no puede cambiarse");
        }
        // Se guarda la mascota completa traida de la base; antes se guardaba el objeto del JSON
        // (solo con id) y la respuesta devolvia la mascota con nombre, especie, etc. en null.
        Mascota mascota = campana.getMascota() == null ? null : validarMascota(campana.getMascota(), existente.getFundacion());
        existente.setTitulo(campana.getTitulo());
        existente.setDescripcion(campana.getDescripcion());
        existente.setCategoria(campana.getCategoria());
        existente.setMetaMonto(campana.getMetaMonto());
        existente.setMontoRecaudado(campana.getMontoRecaudado());
        existente.setFechaInicio(campana.getFechaInicio());
        existente.setFechaFin(campana.getFechaFin());
        existente.setEstado(campana.getEstado());
        existente.setEsUrgente(campana.getEsUrgente());
        existente.setMascota(mascota);
        return convertirAResponse(campanaDonacionRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        CampanaDonacion campana = buscarActivo(campanaDonacionRepository, id, "una campana");
        campana.setEstadoActivo(false);
        campanaDonacionRepository.save(campana);
    }

    private void validarDatosCampana(CampanaDonacion campana) {
        if (campana == null) {
            throw new ReglaDeNegocioException("La campana es obligatoria");
        }
        if (campana.getFundacion() == null || campana.getFundacion().getId() == null) {
            throw new ReglaDeNegocioException("La campana debe indicar una fundacion");
        }
        if (campana.getMetaMonto() == null || campana.getMetaMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ReglaDeNegocioException("La meta de la campana debe ser mayor que cero");
        }
        if (campana.getMontoRecaudado() != null && campana.getMontoRecaudado().compareTo(BigDecimal.ZERO) < 0) {
            throw new ReglaDeNegocioException("El monto recaudado no puede ser negativo");
        }
        if (campana.getFechaInicio() == null) {
            throw new ReglaDeNegocioException("La campana debe indicar una fecha de inicio");
        }
        if (campana.getFechaFin() != null && campana.getFechaFin().isBefore(campana.getFechaInicio())) {
            throw new ReglaDeNegocioException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
    }

    private void resolverRelaciones(CampanaDonacion campana) {
        Long fundacionId = campana.getFundacion().getId();
        Fundacion fundacion = buscarActivo(fundacionRepository, fundacionId, "una fundacion activa");
        campana.setFundacion(fundacion);
        if (campana.getMascota() != null) {
            campana.setMascota(validarMascota(campana.getMascota(), fundacion));
        }
    }

    // Devuelve la mascota de la base si existe, esta activa y pertenece a la fundacion de la campana
    private Mascota validarMascota(Mascota referencia, Fundacion fundacion) {
        if (referencia.getId() == null) {
            throw new ReglaDeNegocioException("La mascota asociada debe indicar un id");
        }
        Mascota mascota = buscarActivo(mascotaRepository, referencia.getId(), "una mascota activa");
        if (mascota.getFundacion() == null || !mascota.getFundacion().getId().equals(fundacion.getId())) {
            throw new ReglaDeNegocioException("La mascota debe pertenecer a la fundacion de la campana");
        }
        return mascota;
    }

    private CampanaDonacion convertirAEntidad(CampanaDonacionRequest request) {
        CampanaDonacion campana = new CampanaDonacion();
        Fundacion fundacion = new Fundacion();
        fundacion.setId(request.fundacionId());
        campana.setFundacion(fundacion);
        if (request.mascotaId() != null) {
            Mascota mascota = new Mascota();
            mascota.setId(request.mascotaId());
            campana.setMascota(mascota);
        }
        campana.setTitulo(request.titulo());
        campana.setDescripcion(request.descripcion());
        campana.setCategoria(request.categoria());
        campana.setMetaMonto(request.metaMonto());
        campana.setFechaInicio(request.fechaInicio());
        campana.setFechaFin(request.fechaFin());
        campana.setEsUrgente(request.esUrgente());
        campana.setMontoRecaudado(BigDecimal.ZERO);
        campana.setEstado(EstadoCampana.ACTIVA);
        return campana;
    }

    private CampanaDonacionResponse convertirAResponse(CampanaDonacion campana) {
        return new CampanaDonacionResponse(
                campana.getId(),
                campana.getFundacion() == null ? null : campana.getFundacion().getId(),
                campana.getMascota() == null ? null : campana.getMascota().getId(),
                campana.getTitulo(),
                campana.getDescripcion(),
                campana.getCategoria(),
                campana.getMetaMonto(),
                campana.getMontoRecaudado(),
                campana.getFechaInicio(),
                campana.getFechaFin(),
                campana.getEstado(),
                campana.getEsUrgente(),
                campana.getEstadoActivo(),
                campana.getFechaCreacion(),
                campana.getFechaActualizacion());
    }

    public CampanaDonacionServiceImpl(
            final CampanaDonacionRepository campanaDonacionRepository,
            final FundacionRepository fundacionRepository,
            final MascotaRepository mascotaRepository) {
        this.campanaDonacionRepository = campanaDonacionRepository;
        this.fundacionRepository = fundacionRepository;
        this.mascotaRepository = mascotaRepository;
    }
}
