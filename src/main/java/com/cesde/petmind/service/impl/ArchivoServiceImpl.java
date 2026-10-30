package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cesde.petmind.dto.ArchivoRequest;
import com.cesde.petmind.dto.ArchivoResponse;
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

@Service
@Transactional
public class ArchivoServiceImpl implements ArchivoService {
    private final ArchivoRepository archivoRepository;
    private final MascotaRepository mascotaRepository;
    private final SolicitudAdopcionRepository solicitudAdopcionRepository;
    private final CampanaDonacionRepository campanaDonacionRepository;
    private final HistoriaRepository historiaRepository;
    private final ReporteAnimalRepository reporteAnimalRepository;

    @Override
    public List<ArchivoResponse> listar() {
        return archivoRepository.findByEstadoActivoTrue().stream().map(this::aResponse).toList();
    }

    @Override
    public ArchivoResponse obtenerPorId(Long id) {
        // Un archivo borrado logicamente se trata igual que uno que no existe
        return aResponse(buscarArchivo(id));
    }

    @Override
    public ArchivoResponse crear(ArchivoRequest request) {
        Archivo archivo = aEntidad(request);
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
        return aResponse(archivoRepository.save(archivo));
    }

    @Override
    public ArchivoResponse actualizar(Long id, ArchivoRequest request) {
        Archivo existente = buscarArchivo(id);
        // Solo cambian los datos del archivo; el registro al que pertenece no cambia
        existente.setUrl(request.url());
        existente.setTipoMedio(request.tipoMedio());
        existente.setCategoria(request.categoria());
        existente.setEsPortada(request.esPortada());
        existente.setOrden(request.orden());
        return aResponse(archivoRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        Archivo archivo = buscarArchivo(id);
        // Borrado logico: no se borra la fila, solo se desactiva
        archivo.setEstadoActivo(false);
        archivoRepository.save(archivo);
    }

    @Override
    public List<ArchivoResponse> listarPorMascota(Long mascotaId) {
        return archivoRepository.findByMascotaIdAndEstadoActivoTrueOrderByOrdenAsc(mascotaId).stream().map(this::aResponse).toList();
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
            throw new ReglaDeNegocioException("El archivo debe pertenecer a un solo registro: mascota, solicitud, campana, historia o reporte");
        }
    }

    private Archivo buscarArchivo(Long id) {
        return buscarActivo(archivoRepository, id, "un archivo");
    }

    private Archivo aEntidad(ArchivoRequest request) {
        Archivo archivo = new Archivo();
        archivo.setUrl(request.url()); archivo.setTipoMedio(request.tipoMedio()); archivo.setCategoria(request.categoria());
        archivo.setEsPortada(request.esPortada()); archivo.setOrden(request.orden());
        if (request.mascotaId() != null) { Mascota e = new Mascota(); e.setId(request.mascotaId()); archivo.setMascota(e); }
        if (request.solicitudId() != null) { SolicitudAdopcion e = new SolicitudAdopcion(); e.setId(request.solicitudId()); archivo.setSolicitud(e); }
        if (request.campanaId() != null) { CampanaDonacion e = new CampanaDonacion(); e.setId(request.campanaId()); archivo.setCampana(e); }
        if (request.historiaId() != null) { Historia e = new Historia(); e.setId(request.historiaId()); archivo.setHistoria(e); }
        if (request.reporteId() != null) { ReporteAnimal e = new ReporteAnimal(); e.setId(request.reporteId()); archivo.setReporte(e); }
        return archivo;
    }

    private ArchivoResponse aResponse(Archivo archivo) {
        return new ArchivoResponse(archivo.getId(), archivo.getMascota() == null ? null : archivo.getMascota().getId(),
                archivo.getSolicitud() == null ? null : archivo.getSolicitud().getId(),
                archivo.getCampana() == null ? null : archivo.getCampana().getId(),
                archivo.getHistoria() == null ? null : archivo.getHistoria().getId(),
                archivo.getReporte() == null ? null : archivo.getReporte().getId(), archivo.getUrl(),
                archivo.getTipoMedio(), archivo.getCategoria(), archivo.getEsPortada(), archivo.getOrden(),
                archivo.getEstadoActivo(), archivo.getFechaCreacion(), archivo.getFechaActualizacion());
    }
    public ArchivoServiceImpl(final ArchivoRepository archivoRepository, final MascotaRepository mascotaRepository, final SolicitudAdopcionRepository solicitudAdopcionRepository, final CampanaDonacionRepository campanaDonacionRepository, final HistoriaRepository historiaRepository, final ReporteAnimalRepository reporteAnimalRepository) {
        this.archivoRepository = archivoRepository;
        this.mascotaRepository = mascotaRepository;
        this.solicitudAdopcionRepository = solicitudAdopcionRepository;
        this.campanaDonacionRepository = campanaDonacionRepository;
        this.historiaRepository = historiaRepository;
        this.reporteAnimalRepository = reporteAnimalRepository;
    }
}
