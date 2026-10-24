package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cesde.petmind.dto.GastoCampanaRequest;
import com.cesde.petmind.dto.GastoCampanaResponse;
import com.cesde.petmind.model.entity.CampanaDonacion;
import com.cesde.petmind.model.entity.GastoCampana;
import com.cesde.petmind.repository.CampanaDonacionRepository;
import com.cesde.petmind.repository.GastoCampanaRepository;
import com.cesde.petmind.service.GastoCampanaService;

@Service
public class GastoCampanaServiceImpl implements GastoCampanaService {
    private final GastoCampanaRepository gastoCampanaRepository;
    private final CampanaDonacionRepository campanaDonacionRepository;

    @Override
    public List<GastoCampanaResponse> listar() {
        return gastoCampanaRepository.findByEstadoActivoTrue().stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public GastoCampanaResponse obtenerPorId(Long id) {
        GastoCampana gasto = buscarActivo(gastoCampanaRepository, id, "un gasto de campana");
        return convertirAResponse(gasto);
    }

    @Override
    public GastoCampanaResponse crear(GastoCampanaRequest request) {
        GastoCampana gasto = convertirAEntidad(request);
        return convertirAResponse(gastoCampanaRepository.save(gasto));
    }

    @Override
    public GastoCampanaResponse actualizar(Long id, GastoCampanaRequest request) {
        GastoCampana existente = buscarActivo(gastoCampanaRepository, id, "un gasto de campana");
        existente.setCampana(buscarActivo(
                campanaDonacionRepository,
                request.getCampanaId(),
                "una campana activa"));
        existente.setConcepto(request.getConcepto());
        existente.setMonto(request.getMonto());
        existente.setOrden(request.getOrden());
        return convertirAResponse(gastoCampanaRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        GastoCampana gasto = buscarActivo(gastoCampanaRepository, id, "un gasto de campana");
        gasto.setEstadoActivo(false);
        gastoCampanaRepository.save(gasto);
    }

    private GastoCampana convertirAEntidad(GastoCampanaRequest request) {
        CampanaDonacion campana = buscarActivo(
                campanaDonacionRepository,
                request.getCampanaId(),
                "una campana activa");
        GastoCampana gasto = new GastoCampana();
        gasto.setCampana(campana);
        gasto.setConcepto(request.getConcepto());
        gasto.setMonto(request.getMonto());
        gasto.setOrden(request.getOrden());
        return gasto;
    }

    private GastoCampanaResponse convertirAResponse(GastoCampana gasto) {
        return new GastoCampanaResponse(
                gasto.getConcepto(),
                gasto.getMonto(),
                gasto.getOrden(),
                gasto.getEstadoActivo(),
                gasto.getFechaCreacion(),
                gasto.getFechaActualizacion());
    }

    public GastoCampanaServiceImpl(
            final GastoCampanaRepository gastoCampanaRepository,
            final CampanaDonacionRepository campanaDonacionRepository) {
        this.gastoCampanaRepository = gastoCampanaRepository;
        this.campanaDonacionRepository = campanaDonacionRepository;
    }
}
