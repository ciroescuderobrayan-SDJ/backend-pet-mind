package com.cesde.petmind.service.impl;

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.Archivo;
import com.cesde.petmind.model.entity.CampanaDonacion;
import com.cesde.petmind.model.entity.Historia;
import com.cesde.petmind.model.entity.Mascota;
import com.cesde.petmind.model.entity.ReporteAnimal;
import com.cesde.petmind.model.entity.SolicitudAdopcion;
import com.cesde.petmind.repository.ArchivoRepository;
import com.cesde.petmind.repository.CampanaDonacionRepository;
import com.cesde.petmind.repository.HistoriaRepository;
import com.cesde.petmind.repository.MascotaRepository;
import com.cesde.petmind.repository.ReporteAnimalRepository;
import com.cesde.petmind.repository.SolicitudAdopcionRepository;
import com.cesde.petmind.service.ArchivoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArchivoServiceImpl implements ArchivoService {

    private final ArchivoRepository archivoRepository;

    private final MascotaRepository mascotaRepository;

    private final SolicitudAdopcionRepository solicitudAdopcionRepository;

    private final CampanaDonacionRepository campanaDonacionRepository;

    private final HistoriaRepository historiaRepository;

    private final ReporteAnimalRepository reporteAnimalRepository;

    @Override
    public List<Archivo> listar() {
        return archivoRepository.findByEstadoActivoTrue();
    }

    @Override
    public Archivo obtenerPorId(Long id) {
        // Un archivo borrado logicamente se trata igual que uno que no existe
        Archivo archivo = buscarActivo(archivoRepository, id, "un archivo");
        return archivo;
    }

    @Override
    public Archivo crear(Archivo archivo) {
        validarUnSoloDueno(archivo);

        // En el JSON solo llega el id del dueno; aca se trae el registro completo de la base
        if (archivo.getMascota() != null) {
            Long mascotaId = archivo.getMascota().getId();
            Mascota mascota = buscarActivo(mascotaRepository, mascotaId, "una mascota");
            archivo.setMascota(mascota);
        }

        if (archivo.getSolicitud() != null) {
            Long solicitudId = archivo.getSolicitud().getId();
            SolicitudAdopcion solicitud = buscarActivo(solicitudAdopcionRepository, solicitudId, "una solicitud");
            archivo.setSolicitud(solicitud);
        }

        if (archivo.getCampana() != null) {
            Long campanaId = archivo.getCampana().getId();
            CampanaDonacion campana = buscarActivo(campanaDonacionRepository, campanaId, "una campana");
            archivo.setCampana(campana);
        }

        if (archivo.getHistoria() != null) {
            Long historiaId = archivo.getHistoria().getId();
            Historia historia = buscarActivo(historiaRepository, historiaId, "una historia");
            archivo.setHistoria(historia);
        }

        if (archivo.getReporte() != null) {
            Long reporteId = archivo.getReporte().getId();
            ReporteAnimal reporte = buscarActivo(reporteAnimalRepository, reporteId, "un reporte");
            archivo.setReporte(reporte);
        }

        return archivoRepository.save(archivo);
    }

    @Override
    public Archivo actualizar(Long id, Archivo archivo) {
        Archivo existente = obtenerPorId(id);

        // Solo cambian los datos del archivo; el registro al que pertenece no cambia
        existente.setUrl(archivo.getUrl());
        existente.setTipoMedio(archivo.getTipoMedio());
        existente.setCategoria(archivo.getCategoria());
        existente.setEsPortada(archivo.getEsPortada());
        existente.setOrden(archivo.getOrden());
        return archivoRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Archivo archivo = obtenerPorId(id);

        // Borrado logico: no se borra la fila, solo se desactiva
        archivo.setEstadoActivo(false);
        archivoRepository.save(archivo);
    }

    @Override
    public List<Archivo> listarPorMascota(Long mascotaId) {
        return archivoRepository.findByMascotaIdAndEstadoActivoTrueOrderByOrdenAsc(mascotaId);
    }

    // Regla de negocio 3: un archivo pertenece a UN solo registro:
    // mascota, solicitud, campana, historia o reporte
    private void validarUnSoloDueno(Archivo archivo) {
        int duenos = 0;
        if (archivo.getMascota() != null) {
            duenos++;
        }
        if (archivo.getSolicitud() != null) {
            duenos++;
        }
        if (archivo.getCampana() != null) {
            duenos++;
        }
        if (archivo.getHistoria() != null) {
            duenos++;
        }
        if (archivo.getReporte() != null) {
            duenos++;
        }

        if (duenos != 1) {
            throw new ReglaDeNegocioException(
                    "El archivo debe pertenecer a un solo registro: mascota, solicitud, campana, historia o reporte");
        }
    }
}
