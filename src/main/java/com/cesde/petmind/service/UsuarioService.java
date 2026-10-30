package com.cesde.petmind.service;

import java.util.List;

import com.cesde.petmind.dto.UsuarioRequest;
import com.cesde.petmind.dto.UsuarioResponse;

public interface UsuarioService {
    List<UsuarioResponse> listar();
    UsuarioResponse obtenerPorId(Long id);
    UsuarioResponse crear(UsuarioRequest usuario);
    UsuarioResponse actualizar(Long id, UsuarioRequest usuario);
    void eliminar(Long id);
}
