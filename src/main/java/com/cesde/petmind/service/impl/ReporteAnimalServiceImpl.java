package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.cesde.petmind.dto.ReporteAnimalRequest;
import com.cesde.petmind.dto.ReporteAnimalResponse;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.model.enums.EstadoReporte;
import com.cesde.petmind.model.entity.ReporteAnimal;
import com.cesde.petmind.repository.ReporteAnimalRepository;
import com.cesde.petmind.repository.UsuarioRepository;
import com.cesde.petmind.service.ReporteAnimalService;

@Service
public class ReporteAnimalServiceImpl implements ReporteAnimalService {
    private final ReporteAnimalRepository reporteAnimalRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<ReporteAnimalResponse> listar() {
        return reporteAnimalRepository.findByEstadoActivoTrue().stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public ReporteAnimalResponse obtenerPorId(Long id) {
        ReporteAnimal reporte = buscarActivo(reporteAnimalRepository, id, "un reporte animal");
        return convertirAResponse(reporte);
    }

    @Override
    public ReporteAnimalResponse crear(ReporteAnimalRequest request) {
        ReporteAnimal reporte = convertirAEntidad(request);
        reporte.setCodigoSeguimiento(generarCodigoSeguimiento());
        reporte.setEstado(EstadoReporte.RECIBIDO);
        reporte.setFechaReporte(LocalDate.now());
        reporte.setEsAnonimo(reporte.getEsAnonimo() == null ? Boolean.FALSE : reporte.getEsAnonimo());
        if (reporte.getUsuario() != null) {
            resolverUsuario(reporte);
        }
        return convertirAResponse(reporteAnimalRepository.save(reporte));
    }

    @Override
    public ReporteAnimalResponse actualizar(Long id, ReporteAnimalRequest request) {
        ReporteAnimal reporte = convertirAEntidad(request);
        ReporteAnimal existente = buscarActivo(reporteAnimalRepository, id, "un reporte animal");
        existente.setTipoCaso(reporte.getTipoCaso());
        existente.setEspecie(reporte.getEspecie());
        existente.setNivelUrgencia(reporte.getNivelUrgencia());
        existente.setDireccion(reporte.getDireccion());
        existente.setLatitud(reporte.getLatitud());
        existente.setLongitud(reporte.getLongitud());
        existente.setDescripcion(reporte.getDescripcion());
        existente.setNombreContacto(reporte.getNombreContacto());
        existente.setTelefonoContacto(reporte.getTelefonoContacto());
        existente.setEsAnonimo(reporte.getEsAnonimo() == null
                ? existente.getEsAnonimo()
                : reporte.getEsAnonimo());
        if (reporte.getUsuario() != null) {
            resolverUsuario(reporte);
            existente.setUsuario(reporte.getUsuario());
        }
        return convertirAResponse(reporteAnimalRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        ReporteAnimal reporte = buscarActivo(reporteAnimalRepository, id, "un reporte animal");
        reporte.setEstadoActivo(false);
        reporteAnimalRepository.save(reporte);
    }

    private ReporteAnimal convertirAEntidad(ReporteAnimalRequest request) {
        ReporteAnimal reporte = new ReporteAnimal();
        reporte.setTipoCaso(request.getTipoCaso());
        reporte.setEspecie(request.getEspecie());
        reporte.setNivelUrgencia(request.getNivelUrgencia());
        reporte.setDireccion(request.getDireccion());
        reporte.setLatitud(request.getLatitud());
        reporte.setLongitud(request.getLongitud());
        reporte.setDescripcion(request.getDescripcion());
        reporte.setNombreContacto(request.getNombreContacto());
        reporte.setTelefonoContacto(request.getTelefonoContacto());
        reporte.setEsAnonimo(request.getEsAnonimo());
        if (request.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(request.getUsuarioId());
            reporte.setUsuario(usuario);
        }
        return reporte;
    }

    private void resolverUsuario(ReporteAnimal reporte) {
        Usuario usuario = buscarActivo(
                usuarioRepository,
                reporte.getUsuario().getId(),
                "un usuario activo");
        reporte.setUsuario(usuario);
    }

    private String generarCodigoSeguimiento() {
        return "REP-" + UUID.randomUUID().toString().replace("-", "")
                .substring(0, 12).toUpperCase();
    }

    private ReporteAnimalResponse convertirAResponse(ReporteAnimal reporte) {
        return new ReporteAnimalResponse(
                reporte.getCodigoSeguimiento(),
                reporte.getTipoCaso(),
                reporte.getEspecie(),
                reporte.getNivelUrgencia(),
                reporte.getDireccion(),
                reporte.getLatitud(),
                reporte.getLongitud(),
                reporte.getDescripcion(),
                reporte.getNombreContacto(),
                reporte.getTelefonoContacto(),
                reporte.getEsAnonimo(),
                reporte.getEstado(),
                reporte.getFechaReporte(),
                reporte.getEstadoActivo(),
                reporte.getFechaCreacion(),
                reporte.getFechaActualizacion());
    }

    public ReporteAnimalServiceImpl(
            final ReporteAnimalRepository reporteAnimalRepository,
            final UsuarioRepository usuarioRepository) {
        this.reporteAnimalRepository = reporteAnimalRepository;
        this.usuarioRepository = usuarioRepository;
    }
}
