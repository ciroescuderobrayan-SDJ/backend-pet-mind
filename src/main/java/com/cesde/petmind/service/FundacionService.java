package com.cesde.petmind.service;

import java.util.List;
import com.cesde.petmind.dto.FundacionRequest;
import com.cesde.petmind.dto.FundacionResponse;

public interface FundacionService {
    List<FundacionResponse> listar();
    FundacionResponse obtenerPorId(Long id);
    FundacionResponse crear(FundacionRequest fundacion);
    FundacionResponse actualizar(Long id, FundacionRequest fundacion);
    void eliminar(Long id);
}
