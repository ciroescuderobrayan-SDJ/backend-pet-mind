package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cesde.petmind.dto.ContactoDto;
import com.cesde.petmind.dto.DireccionDto;
import com.cesde.petmind.dto.FundacionRequest;
import com.cesde.petmind.dto.FundacionResponse;
import com.cesde.petmind.model.entity.Fundacion;
import com.cesde.petmind.repository.FundacionRepository;
import com.cesde.petmind.service.FundacionService;

@Service
public class FundacionServiceImpl implements FundacionService {
    private final FundacionRepository fundacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FundacionResponse> listar() {
        return fundacionRepository.findByEstadoActivoTrue().stream().map(this::aResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FundacionResponse obtenerPorId(Long id) {
        // Una fundacion borrada logicamente se trata igual que una que no existe
        return aResponse(buscarFundacion(id));
    }

    @Override
    @Transactional
    public FundacionResponse crear(FundacionRequest request) {
        Fundacion fundacion = new Fundacion();
        aplicarDatos(fundacion, request);
        return aResponse(fundacionRepository.save(fundacion));
    }

    @Override
    @Transactional
    public FundacionResponse actualizar(Long id, FundacionRequest request) {
        Fundacion existente = buscarFundacion(id);
        aplicarDatos(existente, request);
        return aResponse(fundacionRepository.save(existente));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Fundacion fundacion = buscarFundacion(id);
        // Borrado logico: no se borra la fila, solo se desactiva
        fundacion.setEstadoActivo(false);
        fundacionRepository.save(fundacion);
    }

    private Fundacion buscarFundacion(Long id) {
        return buscarActivo(fundacionRepository, id, "una fundacion");
    }

    private void aplicarDatos(Fundacion fundacion, FundacionRequest request) {
        fundacion.setNombre(request.nombre());
        fundacion.setNit(request.nit());
        fundacion.setRepresentanteLegal(request.representanteLegal());
        fundacion.setDescripcion(request.descripcion());
        fundacion.setDireccion(request.direccion() == null ? null : request.direccion().aEntidad());
        fundacion.setContacto(request.contacto() == null ? null : request.contacto().aEntidad());
        fundacion.setUrlLogo(request.urlLogo());
        fundacion.setUrlPortada(request.urlPortada());
        fundacion.setUrlRut(request.urlRut());
        fundacion.setUrlCamaraComercio(request.urlCamaraComercio());
        fundacion.setEstadoVerificacion(request.estadoVerificacion());
        fundacion.setFechaVerificacion(request.fechaVerificacion());
        fundacion.setLatitud(request.latitud());
        fundacion.setLongitud(request.longitud());
    }

    private FundacionResponse aResponse(Fundacion fundacion) {
        return new FundacionResponse(fundacion.getId(), fundacion.getNombre(), fundacion.getNit(),
                fundacion.getRepresentanteLegal(), fundacion.getDescripcion(), DireccionDto.desde(fundacion.getDireccion()),
                ContactoDto.desde(fundacion.getContacto()), fundacion.getUrlLogo(), fundacion.getUrlPortada(),
                fundacion.getUrlRut(), fundacion.getUrlCamaraComercio(), fundacion.getEstadoVerificacion(),
                fundacion.getFechaVerificacion(), fundacion.getLatitud(), fundacion.getLongitud(), fundacion.getEstadoActivo(),
                fundacion.getFechaCreacion(), fundacion.getFechaActualizacion());
    }
    public FundacionServiceImpl(final FundacionRepository fundacionRepository) {
        this.fundacionRepository = fundacionRepository;
    }
}
