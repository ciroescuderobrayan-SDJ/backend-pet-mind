package com.cesde.petmind.service;

import java.util.List;
import com.cesde.petmind.dto.ArchivoRequest;
import com.cesde.petmind.dto.ArchivoResponse;

public interface ArchivoService {
   
    List<ArchivoResponse> listar();
    ArchivoResponse obtenerPorId(Long id);
    ArchivoResponse crear(ArchivoRequest archivo);
    ArchivoResponse actualizar(Long id, ArchivoRequest archivo);
    void eliminar(Long id);

    List<ArchivoResponse> listarPorMascota(Long mascotaId);
    
}
