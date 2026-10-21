package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cesde.petmind.model.entity.GastoCampana;
import com.cesde.petmind.repository.GastoCampanaRepository;
import com.cesde.petmind.service.GastoCampanaService;

@Service
public class GastoCampanaServiceImpl implements GastoCampanaService {
    private final GastoCampanaRepository gastoCampanaRepository;

    @Override
    public List<GastoCampana> listar() {
        return gastoCampanaRepository.findByEstadoActivoTrue();
    }

    @Override
    public GastoCampana obtenerPorId(Long id) {
        GastoCampana gasto = buscarActivo(gastoCampanaRepository, id, "un gasto de campana");
        return gasto;
    }

    @Override
    public GastoCampana crear(GastoCampana gasto) {
        return gastoCampanaRepository.save(gasto);
    }

    @Override
    public GastoCampana actualizar(Long id, GastoCampana gasto) {
        GastoCampana existente = obtenerPorId(id);
        existente.setConcepto(gasto.getConcepto());
        existente.setMonto(gasto.getMonto());
        existente.setOrden(gasto.getOrden());
        return gastoCampanaRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        GastoCampana gasto = obtenerPorId(id);
        gasto.setEstadoActivo(false);
        gastoCampanaRepository.save(gasto);
    }
    public GastoCampanaServiceImpl(final GastoCampanaRepository gastoCampanaRepository) {
        this.gastoCampanaRepository = gastoCampanaRepository;
    }
}
