package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cesde.petmind.model.entity.ReporteAnimal;
import com.cesde.petmind.repository.ReporteAnimalRepository;
import com.cesde.petmind.service.ReporteAnimalService;

@Service
public class ReporteAnimalServiceImpl implements ReporteAnimalService {
    private final ReporteAnimalRepository reporteAnimalRepository;

    @Override
    public List<ReporteAnimal> listar() {
        return reporteAnimalRepository.findByEstadoActivoTrue();
    }

    @Override
    public ReporteAnimal obtenerPorId(Long id) {
        ReporteAnimal reporte = buscarActivo(reporteAnimalRepository, id, "un reporte animal");
        return reporte;
    }

    @Override
    public ReporteAnimal crear(ReporteAnimal reporte) {
        if (reporte.getFechaReporte() == null) {
            reporte.setFechaReporte(LocalDate.now());
        }
        return reporteAnimalRepository.save(reporte);
    }

    @Override
    public ReporteAnimal actualizar(Long id, ReporteAnimal reporte) {
        ReporteAnimal existente = obtenerPorId(id);
        existente.setCodigoSeguimiento(reporte.getCodigoSeguimiento());
        existente.setTipoCaso(reporte.getTipoCaso());
        existente.setEspecie(reporte.getEspecie());
        existente.setNivelUrgencia(reporte.getNivelUrgencia());
        existente.setDireccion(reporte.getDireccion());
        existente.setLatitud(reporte.getLatitud());
        existente.setLongitud(reporte.getLongitud());
        existente.setDescripcion(reporte.getDescripcion());
        existente.setNombreContacto(reporte.getNombreContacto());
        existente.setTelefonoContacto(reporte.getTelefonoContacto());
        existente.setEsAnonimo(reporte.getEsAnonimo());
        existente.setEstado(reporte.getEstado());
        return reporteAnimalRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        ReporteAnimal reporte = obtenerPorId(id);
        reporte.setEstadoActivo(false);
        reporteAnimalRepository.save(reporte);
    }
    public ReporteAnimalServiceImpl(final ReporteAnimalRepository reporteAnimalRepository) {
        this.reporteAnimalRepository = reporteAnimalRepository;
    }
}
