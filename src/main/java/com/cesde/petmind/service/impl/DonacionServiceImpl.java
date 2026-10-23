package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.dto.DonacionRequest;
import com.cesde.petmind.dto.DonacionResponse;
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
    public List<DonacionResponse> listar() {
        return donacionRepository.findByEstadoActivoTrue().stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public DonacionResponse obtenerPorId(Long id) {
        Donacion donacion = buscarActivo(donacionRepository, id, "una donacion");
        return convertirAResponse(donacion);
    }

    @Override
    public DonacionResponse crear(DonacionRequest request) {
        Donacion donacion = convertirAEntidad(request);
        validarDatosDonacion(donacion);
        resolverRelaciones(donacion);
        donacion.setEstado(EstadoDonacion.PENDIENTE);
        donacion.setFechaDonacion(java.time.LocalDate.now());
        donacion.setEsAnonima(donacion.getEsAnonima() == null ? Boolean.FALSE : donacion.getEsAnonima());
        donacion.setMontoTotal(donacion.getMonto());
        return convertirAResponse(donacionRepository.save(donacion));
    }

    @Override
    public DonacionResponse actualizar(Long id, DonacionRequest request) {
        Donacion donacion = convertirAEntidad(request);
        validarDatosDonacion(donacion);
        Donacion existente = buscarActivo(donacionRepository, id, "una donacion");
        existente.setMonto(donacion.getMonto());
        existente.setFrecuencia(donacion.getFrecuencia());
        existente.setMetodoPago(donacion.getMetodoPago());
        existente.setEsAnonima(donacion.getEsAnonima() == null
                ? existente.getEsAnonima()
                : donacion.getEsAnonima());
        existente.setMensaje(donacion.getMensaje());
        existente.setNombreDonante(donacion.getNombreDonante());
        existente.setCorreoDonante(donacion.getCorreoDonante());
        existente.setDocumentoDonante(donacion.getDocumentoDonante());
        resolverRelaciones(donacion);
        if (donacion.getCampana() != null) {
            existente.setCampana(donacion.getCampana());
        }
        if (donacion.getUsuario() != null) {
            existente.setUsuario(donacion.getUsuario());
        }
        if (donacion.getDonacionOrigen() != null) {
            existente.setDonacionOrigen(donacion.getDonacionOrigen());
        }
        existente.setMontoTotal(donacion.getMonto());
        return convertirAResponse(donacionRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        Donacion donacion = buscarActivo(donacionRepository, id, "una donacion");
        donacion.setEstadoActivo(false);
        donacionRepository.save(donacion);
    }

    @Override
    public List<DonacionResponse> listarPorUsuario(Long usuarioId) {
        return donacionRepository.findByUsuarioIdAndEstadoActivoTrueOrderByFechaDonacionDesc(usuarioId)
                .stream().map(this::convertirAResponse).toList();
    }

    @Override
    public List<DonacionResponse> listarPorCampanaYEstado(Long campanaId, EstadoDonacion estado) {
        return donacionRepository.findByCampanaIdAndEstadoAndEstadoActivoTrue(campanaId, estado)
                .stream().map(this::convertirAResponse).toList();
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
        if (donacion.getDonacionOrigen() != null) {
            Donacion origen = buscarActivo(
                    donacionRepository,
                    donacion.getDonacionOrigen().getId(),
                    "una donacion de origen activa");
            donacion.setDonacionOrigen(origen);
        }
    }

    private Donacion convertirAEntidad(DonacionRequest request) {
        Donacion donacion = new Donacion();
        donacion.setMonto(request.getMonto());
        donacion.setFrecuencia(request.getFrecuencia());
        donacion.setMetodoPago(request.getMetodoPago());
        donacion.setEsAnonima(request.getEsAnonima());
        donacion.setMensaje(request.getMensaje());
        donacion.setNombreDonante(request.getNombreDonante());
        donacion.setCorreoDonante(request.getCorreoDonante());
        donacion.setDocumentoDonante(request.getDocumentoDonante());
        if (request.getCampanaId() != null) {
            CampanaDonacion campana = new CampanaDonacion();
            campana.setId(request.getCampanaId());
            donacion.setCampana(campana);
        }
        if (request.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(request.getUsuarioId());
            donacion.setUsuario(usuario);
        }
        if (request.getDonacionOrigenId() != null) {
            Donacion origen = new Donacion();
            origen.setId(request.getDonacionOrigenId());
            donacion.setDonacionOrigen(origen);
        }
        return donacion;
    }

    private DonacionResponse convertirAResponse(Donacion donacion) {
        return new DonacionResponse(
                donacion.getMonto(),
                donacion.getValorComision(),
                donacion.getMontoTotal(),
                donacion.getFrecuencia(),
                donacion.getFechaDonacion(),
                donacion.getMetodoPago(),
                donacion.getEstado(),
                donacion.getEsAnonima(),
                donacion.getMensaje(),
                donacion.getReferenciaPago(),
                donacion.getNombreDonante(),
                donacion.getCorreoDonante(),
                donacion.getDocumentoDonante(),
                donacion.getEstadoRecurrencia(),
                donacion.getDiaCobro(),
                donacion.getFechaProximoCobro(),
                donacion.getCertificadoCodigo(),
                donacion.getCertificadoUrl(),
                donacion.getCertificadoFecha(),
                donacion.getEstadoActivo(),
                donacion.getFechaCreacion(),
                donacion.getFechaActualizacion());
    }

    public DonacionServiceImpl(final DonacionRepository donacionRepository, final UsuarioRepository usuarioRepository, final CampanaDonacionRepository campanaDonacionRepository) {
        this.donacionRepository = donacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.campanaDonacionRepository = campanaDonacionRepository;
    }
}
