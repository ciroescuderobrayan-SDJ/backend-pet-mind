package com.cesde.petmind.service;

import java.util.List;
import com.cesde.petmind.dto.DonacionRequest;
import com.cesde.petmind.dto.DonacionResponse;
import com.cesde.petmind.model.enums.EstadoDonacion;

public interface DonacionService {
    
    List<DonacionResponse> listar();
    DonacionResponse obtenerPorId(Long id);
    DonacionResponse crear(DonacionRequest request);
    DonacionResponse actualizar(Long id, DonacionRequest request);
    void eliminar(Long id);

    List<DonacionResponse> listarPorUsuario(Long usuarioId);

    List<DonacionResponse> listarPorCampanaYEstado(
            Long campanaId,
            EstadoDonacion estado
    );
    
}
