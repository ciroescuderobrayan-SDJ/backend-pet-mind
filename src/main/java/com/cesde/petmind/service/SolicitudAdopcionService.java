package com.cesde.petmind.service;

import java.util.List;

import com.cesde.petmind.dto.CambioEstadoSolicitudRequest;
import com.cesde.petmind.dto.RespuestaSolicitudRequest;
import com.cesde.petmind.dto.SolicitudAdopcionRequest;
import com.cesde.petmind.dto.SolicitudAdopcionResponse;
import com.cesde.petmind.model.enums.EstadoSolicitud;

public interface SolicitudAdopcionService {
    List<SolicitudAdopcionResponse> listar();
    SolicitudAdopcionResponse obtenerPorId(Long id);
    SolicitudAdopcionResponse crear(SolicitudAdopcionRequest request);
    SolicitudAdopcionResponse actualizar(Long id, SolicitudAdopcionRequest request);
    void eliminar(Long id);
    List<SolicitudAdopcionResponse> listarPorUsuario(Long usuarioId);
    List<SolicitudAdopcionResponse> listarPorFundacionYEstado(
            Long fundacionId,
            EstadoSolicitud estado);
    List<SolicitudAdopcionResponse> listarPorFundacion(Long fundacionId);
    SolicitudAdopcionResponse cambiarEstado(Long id, CambioEstadoSolicitudRequest request);
    SolicitudAdopcionResponse responder(Long id, RespuestaSolicitudRequest request);
}
