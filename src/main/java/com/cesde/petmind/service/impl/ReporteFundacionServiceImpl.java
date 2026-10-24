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
import com.cesde.petmind.model.entity.ReporteAnimal;
import com.cesde.petmind.model.entity.ReporteFundacion;
import com.cesde.petmind.repository.FundacionRepository;
import com.cesde.petmind.repository.ReporteAnimalRepository;
import com.cesde.petmind.repository.ReporteFundacionRepository;
import com.cesde.petmind.service.ReporteFundacionService;

@Service
public class ReporteFundacionServiceImpl implements ReporteFundacionService {
    private final ReporteFundacionRepository reporteFundacionRepository;
    private final ReporteAnimalRepository reporteAnimalRepository;
    private final FundacionRepository fundacionRepository;

    @Override
    public List<ReporteFundacion> listar() {
        return reporteFundacionRepository.findByEstadoActivoTrue();
    }

    @Override
    public ReporteFundacion obtenerPorId(Long id) {
        ReporteFundacion reporteFundacion = buscarActivo(reporteFundacionRepository, id, "un reporte de fundacion");
        return reporteFundacion;
    }

    @Override
    public ReporteFundacion crear(ReporteFundacion reporteFundacion) {
        validarYAsignarRelaciones(reporteFundacion);
        if (reporteFundacion.getFechaNotificacion() == null) {
            reporteFundacion.setFechaNotificacion(LocalDateTime.now());
        }
        return reporteFundacionRepository.save(reporteFundacion);
    }

    @Override
    public ReporteFundacion actualizar(Long id, ReporteFundacion reporteFundacion) {
        ReporteFundacion existente = obtenerPorId(id);
        validarYAsignarRelaciones(reporteFundacion);
        existente.setReporte(reporteFundacion.getReporte());
        existente.setFundacion(reporteFundacion.getFundacion());
        existente.setDistanciaKm(reporteFundacion.getDistanciaKm());
        existente.setEstadoAtencion(reporteFundacion.getEstadoAtencion());
        existente.setFechaNotificacion(reporteFundacion.getFechaNotificacion());
        return reporteFundacionRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        ReporteFundacion reporteFundacion = obtenerPorId(id);
        reporteFundacion.setEstadoActivo(false);
        reporteFundacionRepository.save(reporteFundacion);
    }

    private void validarYAsignarRelaciones(ReporteFundacion reporteFundacion) {
        if (reporteFundacion.getReporte() == null || reporteFundacion.getReporte().getId() == null) {
            throw new ReglaDeNegocioException("El reporte de fundacion debe indicar un reporte animal");
        }
        if (reporteFundacion.getFundacion() == null || reporteFundacion.getFundacion().getId() == null) {
            throw new ReglaDeNegocioException("El reporte de fundacion debe indicar una fundacion");
        }
        Long reporteId = reporteFundacion.getReporte().getId();
        ReporteAnimal reporte = buscarActivo(reporteAnimalRepository, reporteId, "un reporte animal activo");
        Long fundacionId = reporteFundacion.getFundacion().getId();
        Fundacion fundacion = buscarActivo(fundacionRepository, fundacionId, "una fundacion activa");
        reporteFundacion.setReporte(reporte);
        reporteFundacion.setFundacion(fundacion);
    }
    public ReporteFundacionServiceImpl(final ReporteFundacionRepository reporteFundacionRepository, final ReporteAnimalRepository reporteAnimalRepository, final FundacionRepository fundacionRepository) {
        this.reporteFundacionRepository = reporteFundacionRepository;
        this.reporteAnimalRepository = reporteAnimalRepository;
        this.fundacionRepository = fundacionRepository;
    }
}
