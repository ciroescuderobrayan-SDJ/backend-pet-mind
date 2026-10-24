package com.cesde.petmind.service;

import java.util.List;
import com.cesde.petmind.dto.GastoCampanaRequest;
import com.cesde.petmind.dto.GastoCampanaResponse;

public interface GastoCampanaService {
    
    List<GastoCampanaResponse> listar();
    GastoCampanaResponse obtenerPorId(Long id);
    GastoCampanaResponse crear(GastoCampanaRequest request);
    GastoCampanaResponse actualizar(Long id, GastoCampanaRequest request);
    void eliminar(Long id);
    
}
