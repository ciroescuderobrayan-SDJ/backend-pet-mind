package com.cesde.petmind.service;

import java.util.List;

import com.cesde.petmind.dto.MascotaRequest;
import com.cesde.petmind.dto.MascotaResponse;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.EstadoPublicacion;

public interface MascotaService {

    List<MascotaResponse> listar();

    MascotaResponse obtenerPorId(Long id);

    MascotaResponse crear(MascotaRequest request);

    MascotaResponse actualizar(Long id, MascotaRequest request);

    void eliminar(Long id);

    List<MascotaResponse> listarPorEstado(
            EstadoPublicacion estadoPublicacion,
            EstadoAdopcion estadoAdopcion);

    List<MascotaResponse> buscarPorEspecieYCiudad(Especie especie, String ciudad);

    List<MascotaResponse> listarPorFundacion(Long fundacionId);
}
