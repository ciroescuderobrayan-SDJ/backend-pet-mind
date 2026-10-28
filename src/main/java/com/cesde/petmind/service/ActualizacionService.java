package com.cesde.petmind.service;

import java.util.List;

import com.cesde.petmind.dto.ActualizacionRequest;
import com.cesde.petmind.dto.ActualizacionResponse;

public interface ActualizacionService {

    List<ActualizacionResponse> listar();

    ActualizacionResponse obtenerPorId(Long id);

    ActualizacionResponse crear(ActualizacionRequest request);

    ActualizacionResponse actualizar(Long id, ActualizacionRequest request);

    void eliminar(Long id);
}