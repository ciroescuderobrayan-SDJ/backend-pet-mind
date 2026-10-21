package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.CampanaDonacion;
import com.cesde.petmind.model.entity.Donacion;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.model.enums.EstadoDonacion;
import com.cesde.petmind.repository.CampanaDonacionRepository;
import com.cesde.petmind.repository.DonacionRepository;
import com.cesde.petmind.repository.UsuarioRepository;
import com.cesde.petmind.service.DonacionService;

@Service
public class DonacionServiceImpl implements DonacionService {
    private final DonacionRepository donacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final CampanaDonacionRepository campanaDonacionRepository;

    @Override
    public List<Donacion> listar() {
        return donacionRepository.findByEstadoActivoTrue();
    }

    @Override
    public Donacion obtenerPorId(Long id) {
        Donacion donacion = buscarActivo(donacionRepository, id, "una donacion");
        return donacion;
    }

    @Override
    public Donacion crear(Donacion donacion) {
        validarDatosDonacion(donacion);
        resolverRelaciones(donacion);
        return donacionRepository.save(donacion);
    }

    @Override
    public Donacion actualizar(Long id, Donacion donacion) {
        validarDatosDonacion(donacion);
        Donacion existente = obtenerPorId(id);
        existente.setMonto(donacion.getMonto());
        existente.setValorComision(donacion.getValorComision());
        existente.setMontoTotal(donacion.getMontoTotal());
        existente.setFrecuencia(donacion.getFrecuencia());
        existente.setFechaDonacion(donacion.getFechaDonacion());
        existente.setMetodoPago(donacion.getMetodoPago());
        existente.setEstado(donacion.getEstado());
        existente.setEsAnonima(donacion.getEsAnonima());
        existente.setMensaje(donacion.getMensaje());
        existente.setReferenciaPago(donacion.getReferenciaPago());
        existente.setNombreDonante(donacion.getNombreDonante());
        existente.setCorreoDonante(donacion.getCorreoDonante());
        existente.setDocumentoDonante(donacion.getDocumentoDonante());
        existente.setEstadoRecurrencia(donacion.getEstadoRecurrencia());
        existente.setDiaCobro(donacion.getDiaCobro());
        existente.setFechaProximoCobro(donacion.getFechaProximoCobro());
        existente.setCertificadoCodigo(donacion.getCertificadoCodigo());
        existente.setCertificadoUrl(donacion.getCertificadoUrl());
        existente.setCertificadoFecha(donacion.getCertificadoFecha());
        return donacionRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Donacion donacion = obtenerPorId(id);
        donacion.setEstadoActivo(false);
        donacionRepository.save(donacion);
    }

    @Override
    public List<Donacion> listarPorUsuario(Long usuarioId) {
        return donacionRepository.findByUsuarioIdAndEstadoActivoTrueOrderByFechaDonacionDesc(usuarioId);
    }

    @Override
    public List<Donacion> listarPorCampanaYEstado(Long campanaId, EstadoDonacion estado) {
        return donacionRepository.findByCampanaIdAndEstadoAndEstadoActivoTrue(campanaId, estado);
    }

    private void validarDatosDonacion(Donacion donacion) {
        if (donacion == null) {
            throw new ReglaDeNegocioException("La donacion es obligatoria");
        }
        if (donacion.getMonto() == null || donacion.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ReglaDeNegocioException("El monto de la donacion debe ser mayor que cero");
        }
        if (donacion.getMetodoPago() == null) {
            throw new ReglaDeNegocioException("La donacion debe tener un metodo de pago valido");
        }
    }

    private void resolverRelaciones(Donacion donacion) {
        if (donacion.getUsuario() != null) {
            Long usuarioId = donacion.getUsuario().getId();
            Usuario usuario = buscarActivo(usuarioRepository, usuarioId, "un usuario activo");
            donacion.setUsuario(usuario);
        }
        if (donacion.getCampana() != null) {
            Long campanaId = donacion.getCampana().getId();
            CampanaDonacion campana = buscarActivo(campanaDonacionRepository, campanaId, "una campana activa");
            donacion.setCampana(campana);
        }
    }
    public DonacionServiceImpl(final DonacionRepository donacionRepository, final UsuarioRepository usuarioRepository, final CampanaDonacionRepository campanaDonacionRepository) {
        this.donacionRepository = donacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.campanaDonacionRepository = campanaDonacionRepository;
    }
}
