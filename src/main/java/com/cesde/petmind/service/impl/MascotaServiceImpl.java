package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cesde.petmind.dto.MascotaRequest;
import com.cesde.petmind.dto.MascotaResponse;
import com.cesde.petmind.model.entity.Fundacion;
import com.cesde.petmind.model.entity.Mascota;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.EstadoPublicacion;
import com.cesde.petmind.repository.FundacionRepository;
import com.cesde.petmind.repository.MascotaRepository;
import com.cesde.petmind.service.MascotaService;

@Service
@Transactional
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;
    private final FundacionRepository fundacionRepository;

    @Override
    public List<MascotaResponse> listar() {
        return mascotaRepository.findByEstadoActivoTrue()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public MascotaResponse obtenerPorId(Long id) {
        Mascota mascota = buscarActivo(mascotaRepository, id, "una mascota");
        return convertirAResponse(mascota);
    }

    @Override
    public MascotaResponse crear(MascotaRequest request) {
        Mascota mascota = convertirAEntidad(request);

        if (mascota.getEstadoPublicacion() == EstadoPublicacion.PUBLICADA
                && mascota.getFechaPublicacion() == null) {
            mascota.setFechaPublicacion(LocalDateTime.now());
        }

        mascota.setContadorVisitas(0);

        return convertirAResponse(mascotaRepository.save(mascota));
    }

    @Override
    public MascotaResponse actualizar(Long id, MascotaRequest request) {
        Mascota existente = buscarActivo(mascotaRepository, id, "una mascota");
        Mascota mascota = convertirAEntidad(request);

        existente.setFundacion(mascota.getFundacion());
        existente.setNombre(mascota.getNombre());
        existente.setEspecie(mascota.getEspecie());
        existente.setRaza(mascota.getRaza());
        existente.setEdadMeses(mascota.getEdadMeses());
        existente.setTamano(mascota.getTamano());
        existente.setSexo(mascota.getSexo());
        existente.setCualidadesFisicas(mascota.getCualidadesFisicas());
        existente.setCiudad(mascota.getCiudad());
        existente.setDescripcion(mascota.getDescripcion());
        existente.setHistoria(mascota.getHistoria());
        existente.setEtiquetas(mascota.getEtiquetas());
        existente.setAfinidad(mascota.getAfinidad());
        existente.setEstadoAdopcion(mascota.getEstadoAdopcion());
        existente.setEstadoPublicacion(mascota.getEstadoPublicacion());
        existente.setEsterilizado(mascota.getEsterilizado());
        existente.setVacunasAlDia(mascota.getVacunasAlDia());
        existente.setDesparasitado(mascota.getDesparasitado());
        existente.setTieneMicrochip(mascota.getTieneMicrochip());
        existente.setAptoApadrinamiento(mascota.getAptoApadrinamiento());

        if (existente.getEstadoPublicacion() == EstadoPublicacion.PUBLICADA
                && existente.getFechaPublicacion() == null) {
            existente.setFechaPublicacion(LocalDateTime.now());
        }

        return convertirAResponse(mascotaRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        Mascota mascota = buscarActivo(mascotaRepository, id, "una mascota");
        mascota.setEstadoActivo(false);
        mascotaRepository.save(mascota);
    }

    @Override
    public List<MascotaResponse> listarPorEstado(
            EstadoPublicacion estadoPublicacion,
            EstadoAdopcion estadoAdopcion) {

        return mascotaRepository
                .findByEstadoPublicacionAndEstadoAdopcionAndEstadoActivoTrue(
                        estadoPublicacion,
                        estadoAdopcion)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public List<MascotaResponse> buscarPorEspecieYCiudad(
            Especie especie,
            String ciudad) {

        return mascotaRepository
                .findByEspecieAndCiudadIgnoreCaseAndEstadoActivoTrue(especie, ciudad)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public List<MascotaResponse> listarPorFundacion(Long fundacionId) {
        return mascotaRepository
                .findByFundacionIdAndEstadoActivoTrue(fundacionId)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    private Mascota convertirAEntidad(MascotaRequest request) {
        Fundacion fundacion = buscarActivo(
                fundacionRepository,
                request.fundacionId(),
                "una fundación activa");

        Mascota mascota = new Mascota();
        mascota.setFundacion(fundacion);
        mascota.setNombre(request.nombre());
        mascota.setEspecie(request.especie());
        mascota.setRaza(request.raza());
        mascota.setEdadMeses(request.edadMeses());
        mascota.setTamano(request.tamano());
        mascota.setSexo(request.sexo());
        mascota.setCualidadesFisicas(request.cualidadesFisicas());
        mascota.setCiudad(request.ciudad());
        mascota.setDescripcion(request.descripcion());
        mascota.setHistoria(request.historia());
        mascota.setEtiquetas(request.etiquetas());
        mascota.setAfinidad(request.afinidad());
        mascota.setEstadoAdopcion(request.estadoAdopcion());
        mascota.setEstadoPublicacion(request.estadoPublicacion());
        mascota.setEsterilizado(request.esterilizado());
        mascota.setVacunasAlDia(request.vacunasAlDia());
        mascota.setDesparasitado(request.desparasitado());
        mascota.setTieneMicrochip(request.tieneMicrochip());
        mascota.setAptoApadrinamiento(request.aptoApadrinamiento());

        return mascota;
    }

    private MascotaResponse convertirAResponse(Mascota mascota) {
        return new MascotaResponse(
                mascota.getId(),
                mascota.getFundacion() == null ? null : mascota.getFundacion().getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getEdadMeses(),
                mascota.getTamano(),
                mascota.getSexo(),
                mascota.getCualidadesFisicas(),
                mascota.getCiudad(),
                mascota.getDescripcion(),
                mascota.getHistoria(),
                mascota.getEtiquetas(),
                mascota.getAfinidad(),
                mascota.getEstadoAdopcion(),
                mascota.getEstadoPublicacion(),
                mascota.getEsterilizado(),
                mascota.getVacunasAlDia(),
                mascota.getDesparasitado(),
                mascota.getTieneMicrochip(),
                mascota.getAptoApadrinamiento(),
                mascota.getFechaPublicacion(),
                mascota.getContadorVisitas(),
                mascota.getEstadoActivo(),
                mascota.getFechaCreacion(),
                mascota.getFechaActualizacion());
    }

    public MascotaServiceImpl(
            MascotaRepository mascotaRepository,
            FundacionRepository fundacionRepository) {
        this.mascotaRepository = mascotaRepository;
        this.fundacionRepository = fundacionRepository;
    }
}
