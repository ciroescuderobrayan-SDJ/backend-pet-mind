package com.cesde.petmind.service;

import java.util.List;

import com.cesde.petmind.dto.ReporteFundacionRequest;
import com.cesde.petmind.dto.ReporteFundacionResponse;

public interface ReporteFundacionService {

    List<ReporteFundacionResponse> listar();

    ReporteFundacionResponse obtenerPorId(Long id);

    ReporteFundacionResponse crear(ReporteFundacionRequest request);

    ReporteFundacionResponse actualizar(Long id, ReporteFundacionRequest request);

    void eliminar(Long id);
}