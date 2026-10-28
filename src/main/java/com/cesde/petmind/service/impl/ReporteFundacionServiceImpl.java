package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cesde.petmind.dto.ReporteFundacionRequest;
import com.cesde.petmind.dto.ReporteFundacionResponse;
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
    public List<ReporteFundacionResponse> listar() {
        return reporteFundacionRepository.findByEstadoActivoTrue()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public ReporteFundacionResponse obtenerPorId(Long id) {
        ReporteFundacion reporteFundacion = buscarActivo(
                reporteFundacionRepository,
                id,
                "un reporte de fundación");

        return convertirAResponse(reporteFundacion);
    }

    @Override
    public ReporteFundacionResponse crear(ReporteFundacionRequest request) {
        ReporteFundacion reporteFundacion = convertirAEntidad(request);

        if (reporteFundacion.getFechaNotificacion() == null) {
            reporteFundacion.setFechaNotificacion(LocalDateTime.now());
        }

        return convertirAResponse(
                reporteFundacionRepository.save(reporteFundacion));
    }

    @Override
    public ReporteFundacionResponse actualizar(
            Long id,
            ReporteFundacionRequest request) {

        ReporteFundacion existente = buscarActivo(
                reporteFundacionRepository,
                id,
                "un reporte de fundación");

        ReporteFundacion reporteFundacion = convertirAEntidad(request);

        existente.setReporte(reporteFundacion.getReporte());
        existente.setFundacion(reporteFundacion.getFundacion());
        existente.setDistanciaKm(reporteFundacion.getDistanciaKm());
        existente.setEstadoAtencion(reporteFundacion.getEstadoAtencion());

        if (reporteFundacion.getFechaNotificacion() != null) {
            existente.setFechaNotificacion(
                    reporteFundacion.getFechaNotificacion());
        }

        return convertirAResponse(
                reporteFundacionRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        ReporteFundacion reporteFundacion = buscarActivo(
                reporteFundacionRepository,
                id,
                "un reporte de fundación");

        reporteFundacion.setEstadoActivo(false);
        reporteFundacionRepository.save(reporteFundacion);
    }

    private ReporteFundacion convertirAEntidad(
            ReporteFundacionRequest request) {

        ReporteAnimal reporte = buscarActivo(
                reporteAnimalRepository,
                request.reporteId(),
                "un reporte animal activo");

        Fundacion fundacion = buscarActivo(
                fundacionRepository,
                request.fundacionId(),
                "una fundación activa");

        ReporteFundacion reporteFundacion = new ReporteFundacion();
        reporteFundacion.setReporte(reporte);
        reporteFundacion.setFundacion(fundacion);
        reporteFundacion.setDistanciaKm(request.distanciaKm());
        reporteFundacion.setEstadoAtencion(request.estadoAtencion());
        reporteFundacion.setFechaNotificacion(request.fechaNotificacion());

        return reporteFundacion;
    }

    private ReporteFundacionResponse convertirAResponse(
            ReporteFundacion reporteFundacion) {

        return new ReporteFundacionResponse(
                reporteFundacion.getDistanciaKm(),
                reporteFundacion.getEstadoAtencion(),
                reporteFundacion.getFechaNotificacion(),
                reporteFundacion.getEstadoActivo(),
                reporteFundacion.getFechaCreacion(),
                reporteFundacion.getFechaActualizacion());
    }

    public ReporteFundacionServiceImpl(
            ReporteFundacionRepository reporteFundacionRepository,
            ReporteAnimalRepository reporteAnimalRepository,
            FundacionRepository fundacionRepository) {
        this.reporteFundacionRepository = reporteFundacionRepository;
        this.reporteAnimalRepository = reporteAnimalRepository;
        this.fundacionRepository = fundacionRepository;
    }
}