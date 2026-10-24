package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.Actualizacion;
import com.cesde.petmind.model.entity.CampanaDonacion;
import com.cesde.petmind.model.entity.Historia;
import com.cesde.petmind.repository.ActualizacionRepository;
import com.cesde.petmind.repository.CampanaDonacionRepository;
import com.cesde.petmind.repository.HistoriaRepository;
import com.cesde.petmind.service.ActualizacionService;

@Service
public class ActualizacionServiceImpl implements ActualizacionService {
    private final ActualizacionRepository actualizacionRepository;
    private final CampanaDonacionRepository campanaDonacionRepository;
    private final HistoriaRepository historiaRepository;

    @Override
    public List<Actualizacion> listar() {
        return actualizacionRepository.findByEstadoActivoTrue();
    }

    @Override
    public Actualizacion obtenerPorId(Long id) {
        Actualizacion actualizacion = buscarActivo(actualizacionRepository, id, "una actualizacion");
        return actualizacion;
    }

    @Override
    public Actualizacion crear(Actualizacion actualizacion) {
        validarYAsignarRelacion(actualizacion);
        if (actualizacion.getFechaEvento() == null) {
            actualizacion.setFechaEvento(LocalDateTime.now());
        }
        return actualizacionRepository.save(actualizacion);
    }

    @Override
    public Actualizacion actualizar(Long id, Actualizacion actualizacion) {
        Actualizacion existente = obtenerPorId(id);
        validarYAsignarRelacion(actualizacion);
        existente.setCampana(actualizacion.getCampana());
        existente.setHistoria(actualizacion.getHistoria());
        existente.setTitulo(actualizacion.getTitulo());
        existente.setContenido(actualizacion.getContenido());
        existente.setFechaEvento(actualizacion.getFechaEvento());
        existente.setOrden(actualizacion.getOrden());
        return actualizacionRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Actualizacion actualizacion = obtenerPorId(id);
        actualizacion.setEstadoActivo(false);
        actualizacionRepository.save(actualizacion);
    }

    private void validarYAsignarRelacion(Actualizacion actualizacion) {
        int relaciones = 0;
        if (actualizacion.getCampana() != null) {
            relaciones++;
        }
        if (actualizacion.getHistoria() != null) {
            relaciones++;
        }
        if (relaciones != 1) {
            throw new ReglaDeNegocioException("La actualizacion debe pertenecer a una campana o a una historia");
        }
        if (actualizacion.getCampana() != null) {
            if (actualizacion.getCampana().getId() == null) {
                throw new ReglaDeNegocioException("La campana debe tener un id");
            }
            Long campanaId = actualizacion.getCampana().getId();
            CampanaDonacion campana = buscarActivo(campanaDonacionRepository, campanaId, "una campana activa");
            actualizacion.setCampana(campana);
            actualizacion.setHistoria(null);
        }
        if (actualizacion.getHistoria() != null) {
            if (actualizacion.getHistoria().getId() == null) {
                throw new ReglaDeNegocioException("La historia debe tener un id");
            }
            Long historiaId = actualizacion.getHistoria().getId();
            Historia historia = buscarActivo(historiaRepository, historiaId, "una historia activa");
            actualizacion.setHistoria(historia);
            actualizacion.setCampana(null);
        }
    }
    public ActualizacionServiceImpl(final ActualizacionRepository actualizacionRepository, final CampanaDonacionRepository campanaDonacionRepository, final HistoriaRepository historiaRepository) {
        this.actualizacionRepository = actualizacionRepository;
        this.campanaDonacionRepository = campanaDonacionRepository;
        this.historiaRepository = historiaRepository;
    }
}
