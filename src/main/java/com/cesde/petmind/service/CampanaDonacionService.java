package com.cesde.petmind.service;

import java.util.List;
import com.cesde.petmind.dto.CampanaDonacionRequest;
import com.cesde.petmind.dto.CampanaDonacionResponse;
import com.cesde.petmind.model.enums.EstadoCampana;

public interface CampanaDonacionService {
    List<CampanaDonacionResponse> listar();
    List<CampanaDonacionResponse> listarPorEstado(EstadoCampana estado);
    CampanaDonacionResponse obtenerPorId(Long id);
    CampanaDonacionResponse crear(CampanaDonacionRequest request);
    CampanaDonacionResponse actualizar(Long id, CampanaDonacionRequest request);
    void eliminar(Long id);
}