package com.cesde.petmind.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.cesde.petmind.dto.*;
import com.cesde.petmind.exceptions.GlobalExceptionHandler;
import com.cesde.petmind.model.enums.*;
import com.cesde.petmind.service.*;

class ControladoresEmanuelTest {
    private final ArchivoService archivos = mock(ArchivoService.class);
    private final FavoritoService favoritos = mock(FavoritoService.class);
    private final NotificacionService notificaciones = mock(NotificacionService.class);
    private MockMvc mvc;

    @BeforeEach
    void preparar() {
        mvc = MockMvcBuilders.standaloneSetup(new ArchivoController(archivos), new FavoritoController(favoritos),
                new NotificacionController(notificaciones)).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void recibirYDevolverIdsEnLosTresRecursos() throws Exception {
        when(archivos.crear(any())).thenReturn(new ArchivoResponse(7L, 2L, null, null, null, null,
                "https://ejemplo.com/foto", TipoMedio.IMAGEN, CategoriaArchivo.GALERIA, false, 0, true, null, null));
        when(favoritos.crear(any())).thenReturn(new FavoritoResponse(8L, 1L, 2L, null, null, true, null, null));
        when(notificaciones.crear(any())).thenReturn(new NotificacionResponse(9L, 1L, TipoNotificacion.SISTEMA,
                "Aviso", "Mensaje", null, false, true, null, null));
        mvc.perform(post("/api/archivos").contentType(MediaType.APPLICATION_JSON).content("""
                {"mascotaId":2,"url":"https://ejemplo.com/foto","tipoMedio":"IMAGEN","categoria":"GALERIA","esPortada":false}
                """)).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(7)).andExpect(jsonPath("$.mascotaId").value(2));
        mvc.perform(post("/api/favoritos").contentType(MediaType.APPLICATION_JSON).content("{\"usuarioId\":1,\"mascotaId\":2}"))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(8)).andExpect(jsonPath("$.usuarioId").value(1));
        mvc.perform(post("/api/notificaciones").contentType(MediaType.APPLICATION_JSON).content("""
                {"usuarioId":1,"tipo":"SISTEMA","titulo":"Aviso","mensaje":"Mensaje","leida":false}
                """)).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(9)).andExpect(jsonPath("$.usuarioId").value(1));
        verify(archivos).crear(any()); verify(favoritos).crear(any()); verify(notificaciones).crear(any());
    }

    @Test
    void validacionImpideDatosIncompletos() throws Exception {
        mvc.perform(post("/api/archivos").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje", containsString("url:")));
        mvc.perform(post("/api/favoritos").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje", containsString("usuarioId:")));
        mvc.perform(post("/api/notificaciones").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensaje", containsString("titulo:")));
        verify(archivos, never()).crear(any()); verify(favoritos, never()).crear(any()); verify(notificaciones, never()).crear(any());
    }
}
