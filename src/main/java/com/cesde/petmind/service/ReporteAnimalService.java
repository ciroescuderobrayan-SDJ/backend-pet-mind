package com.cesde.petmind.service;

import java.util.List;
import com.cesde.petmind.dto.ReporteAnimalRequest;
import com.cesde.petmind.dto.ReporteAnimalResponse;

public interface ReporteAnimalService {
    List<ReporteAnimalResponse> listar();
    ReporteAnimalResponse obtenerPorId(Long id);
    ReporteAnimalResponse crear(ReporteAnimalRequest request);
    ReporteAnimalResponse actualizar(Long id, ReporteAnimalRequest request);
    void eliminar(Long id);
}
