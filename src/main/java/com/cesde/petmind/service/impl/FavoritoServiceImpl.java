package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cesde.petmind.dto.FavoritoRequest;
import com.cesde.petmind.dto.FavoritoResponse;
import com.cesde.petmind.exceptions.RecursoDuplicadoException;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.CampanaDonacion;
import com.cesde.petmind.model.entity.Favorito;
import com.cesde.petmind.model.entity.Fundacion;
import com.cesde.petmind.model.entity.Mascota;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.repository.CampanaDonacionRepository;
import com.cesde.petmind.repository.FavoritoRepository;
import com.cesde.petmind.repository.FundacionRepository;
import com.cesde.petmind.repository.MascotaRepository;
import com.cesde.petmind.repository.UsuarioRepository;
import com.cesde.petmind.service.FavoritoService;

@Service
@Transactional
public class FavoritoServiceImpl implements FavoritoService {
    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;
    private final CampanaDonacionRepository campanaDonacionRepository;
    private final FundacionRepository fundacionRepository;

    @Override
    public List<FavoritoResponse> listar() {
        return favoritoRepository.findByEstadoActivoTrue().stream().map(this::aResponse).toList();
    }

    @Override
    public FavoritoResponse obtenerPorId(Long id) {
        // Un favorito borrado logicamente se trata igual que uno que no existe
        return aResponse(buscarFavorito(id));
    }

    @Override
    public FavoritoResponse crear(FavoritoRequest request) {
        Favorito favorito = new Favorito();
        if (request.usuarioId() != null) {
            Usuario usuario = new Usuario(); usuario.setId(request.usuarioId()); favorito.setUsuario(usuario);
        }
        if (request.mascotaId() != null) {
            Mascota mascota = new Mascota(); mascota.setId(request.mascotaId()); favorito.setMascota(mascota);
        }
        if (request.campanaId() != null) {
            CampanaDonacion campana = new CampanaDonacion(); campana.setId(request.campanaId()); favorito.setCampana(campana);
        }
        if (request.fundacionId() != null) {
            Fundacion fundacion = new Fundacion(); fundacion.setId(request.fundacionId()); favorito.setFundacion(fundacion);
        }
        validarUnSoloDestino(favorito);
        if (favorito.getUsuario() == null) {
            throw new ReglaDeNegocioException("El favorito debe indicar el usuario que lo guarda");
        }
        // En el JSON solo llegan los id; aca se traen los registros completos de la base
        Long usuarioId = favorito.getUsuario().getId();
        Usuario usuario = buscarActivo(usuarioRepository, usuarioId, "un usuario");
        favorito.setUsuario(usuario);
        if (favorito.getMascota() != null) {
            Long mascotaId = favorito.getMascota().getId();
            Mascota mascota = buscarActivo(mascotaRepository, mascotaId, "una mascota");
            favorito.setMascota(mascota);
        }
        if (favorito.getCampana() != null) {
            Long campanaId = favorito.getCampana().getId();
            CampanaDonacion campana = buscarActivo(campanaDonacionRepository, campanaId, "una campana");
            favorito.setCampana(campana);
        }
        if (favorito.getFundacion() != null) {
            Long fundacionId = favorito.getFundacion().getId();
            Fundacion fundacion = buscarActivo(fundacionRepository, fundacionId, "una fundacion");
            favorito.setFundacion(fundacion);
        }
        validarNoRepetido(favorito);
        return aResponse(favoritoRepository.save(favorito));
    }

    @Override
    public void eliminar(Long id) {
        Favorito favorito = buscarFavorito(id);
        // Borrado logico: no se borra la fila, solo se desactiva
        favorito.setEstadoActivo(false);
        favoritoRepository.save(favorito);
    }

    @Override
    public List<FavoritoResponse> listarPorUsuario(Long usuarioId) {
        return favoritoRepository.findByUsuarioIdAndEstadoActivoTrue(usuarioId).stream().map(this::aResponse).toList();
    }

    // Regla de negocio: el mismo usuario no puede guardar dos veces lo mismo.
    // El frontend trata el favorito como "marcado / no marcado", asi que un duplicado lo descuadra.
    private void validarNoRepetido(Favorito favorito) {
        Long usuarioId = favorito.getUsuario().getId();
        boolean repetido;
        if (favorito.getMascota() != null) {
            repetido = favoritoRepository.existsByUsuarioIdAndMascotaIdAndEstadoActivoTrue(usuarioId, favorito.getMascota().getId());
        } else if (favorito.getCampana() != null) {
            repetido = favoritoRepository.existsByUsuarioIdAndCampanaIdAndEstadoActivoTrue(usuarioId, favorito.getCampana().getId());
        } else {
            repetido = favoritoRepository.existsByUsuarioIdAndFundacionIdAndEstadoActivoTrue(usuarioId, favorito.getFundacion().getId());
        }
        if (repetido) {
            throw new RecursoDuplicadoException("El usuario ya tiene este elemento en sus favoritos");
        }
    }

    // Regla de negocio 3: un favorito apunta a UNA sola cosa: mascota, campana o fundacion
    private void validarUnSoloDestino(Favorito favorito) {
        int destinos = 0;
        if (favorito.getMascota() != null) {
            destinos++;
        }
        if (favorito.getCampana() != null) {
            destinos++;
        }
        if (favorito.getFundacion() != null) {
            destinos++;
        }
        if (destinos != 1) {
            throw new ReglaDeNegocioException("El favorito debe apuntar a una sola cosa: una mascota, una campana o una fundacion");
        }
    }

    private Favorito buscarFavorito(Long id) {
        return buscarActivo(favoritoRepository, id, "un favorito");
    }

    private FavoritoResponse aResponse(Favorito favorito) {
        return new FavoritoResponse(favorito.getId(), favorito.getUsuario() == null ? null : favorito.getUsuario().getId(),
                favorito.getMascota() == null ? null : favorito.getMascota().getId(),
                favorito.getCampana() == null ? null : favorito.getCampana().getId(),
                favorito.getFundacion() == null ? null : favorito.getFundacion().getId(),
                favorito.getEstadoActivo(), favorito.getFechaCreacion(), favorito.getFechaActualizacion());
    }
    public FavoritoServiceImpl(final FavoritoRepository favoritoRepository, final UsuarioRepository usuarioRepository, final MascotaRepository mascotaRepository, final CampanaDonacionRepository campanaDonacionRepository, final FundacionRepository fundacionRepository) {
        this.favoritoRepository = favoritoRepository;
        this.usuarioRepository = usuarioRepository;
        this.mascotaRepository = mascotaRepository;
        this.campanaDonacionRepository = campanaDonacionRepository;
        this.fundacionRepository = fundacionRepository;
    }
}
