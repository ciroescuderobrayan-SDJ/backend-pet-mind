package com.cesde.petmind.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.cesde.petmind.exceptions.GlobalExceptionHandler;
import com.cesde.petmind.service.ActualizacionService;
import com.cesde.petmind.service.HistoriaService;
import com.cesde.petmind.service.ReporteFundacionService;

class ControladoresSantiagoTest {
    private final HistoriaService historias = mock(HistoriaService.class);
    private final ActualizacionService actualizaciones = mock(ActualizacionService.class);
    private final ReporteFundacionService reportes = mock(ReporteFundacionService.class);
    private MockMvc mvc;

    @BeforeEach
    void preparar() {
        mvc = MockMvcBuilders.standaloneSetup(new HistoriaController(historias),
                new ActualizacionController(actualizaciones), new ReporteFundacionController(reportes))
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void rutasDeLecturaYEliminacionDeleganEnSusServicios() throws Exception {
        mvc.perform(get("/api/historias")).andExpect(status().isOk());
        mvc.perform(get("/api/actualizaciones/2")).andExpect(status().isOk());
        mvc.perform(delete("/api/reportes-fundaciones/3")).andExpect(status().isNoContent());
        verify(historias).listar();
        verify(actualizaciones).obtenerPorId(2L);
        verify(reportes).eliminar(3L);
    }

    @Test
    void crearConDatosInvalidosDevuelveCampoYNoLlamaAlServicio() throws Exception {
        mvc.perform(post("/api/historias").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje", containsString("titulo:")));
        mvc.perform(post("/api/actualizaciones").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje", containsString("titulo:")));
        mvc.perform(post("/api/reportes-fundaciones").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje", containsString("reporteId:")));
        verify(historias, never()).crear(any());
        verify(actualizaciones, never()).crear(any());
        verify(reportes, never()).crear(any());
    }

    @Test
    void solicitudesValidasLleganAlServicioEnPostYPut() throws Exception {
        String historia = """
                {"fundacionId":1,"titulo":"Historia","resumen":"Resumen","contenido":"Contenido","tipo":"RESCATE"}
                """;
        String actualizacion = """
                {"historiaId":2,"titulo":"Avance","contenido":"Contenido","fechaEvento":"2026-10-30T08:00:00"}
                """;
        String reporte = """
                {"reporteId":1,"fundacionId":2,"estadoAtencion":"PENDIENTE"}
                """;
        mvc.perform(post("/api/historias").contentType(MediaType.APPLICATION_JSON).content(historia)).andExpect(status().isCreated());
        mvc.perform(put("/api/historias/1").contentType(MediaType.APPLICATION_JSON).content(historia)).andExpect(status().isOk());
        mvc.perform(post("/api/actualizaciones").contentType(MediaType.APPLICATION_JSON).content(actualizacion)).andExpect(status().isCreated());
        mvc.perform(put("/api/actualizaciones/2").contentType(MediaType.APPLICATION_JSON).content(actualizacion)).andExpect(status().isOk());
        mvc.perform(post("/api/reportes-fundaciones").contentType(MediaType.APPLICATION_JSON).content(reporte)).andExpect(status().isCreated());
        mvc.perform(put("/api/reportes-fundaciones/3").contentType(MediaType.APPLICATION_JSON).content(reporte)).andExpect(status().isOk());
        verify(historias).crear(any()); verify(historias).actualizar(org.mockito.ArgumentMatchers.eq(1L), any());
        verify(actualizaciones).crear(any()); verify(actualizaciones).actualizar(org.mockito.ArgumentMatchers.eq(2L), any());
        verify(reportes).crear(any()); verify(reportes).actualizar(org.mockito.ArgumentMatchers.eq(3L), any());
    }
}
