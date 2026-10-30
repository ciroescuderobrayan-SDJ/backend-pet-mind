package com.cesde.petmind.service;

import java.util.List;
import com.cesde.petmind.dto.FavoritoRequest;
import com.cesde.petmind.dto.FavoritoResponse;

public interface FavoritoService {
    
    List<FavoritoResponse> listar();
    FavoritoResponse obtenerPorId(Long id);
    FavoritoResponse crear(FavoritoRequest favorito);
    void eliminar(Long id);

    List<FavoritoResponse> listarPorUsuario(Long usuarioId);
    
}
