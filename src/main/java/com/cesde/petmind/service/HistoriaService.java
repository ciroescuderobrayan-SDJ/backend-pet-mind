package com.cesde.petmind.service;

import java.util.List;

import com.cesde.petmind.dto.HistoriaRequest;
import com.cesde.petmind.dto.HistoriaResponse;

public interface HistoriaService {

    List<HistoriaResponse> listar();

    HistoriaResponse obtenerPorId(Long id);

    HistoriaResponse crear(HistoriaRequest request);

    HistoriaResponse actualizar(Long id, HistoriaRequest request);

    void eliminar(Long id);
}