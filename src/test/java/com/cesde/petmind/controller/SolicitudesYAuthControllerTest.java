package com.cesde.petmind.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cesde.petmind.exceptions.AccesoDenegadoException;
import com.cesde.petmind.exceptions.CredencialesInvalidasException;
import com.cesde.petmind.exceptions.GlobalExceptionHandler;
import com.cesde.petmind.service.SolicitudAdopcionService;
import com.cesde.petmind.service.UsuarioService;

class SolicitudesYAuthControllerTest {
    private final SolicitudAdopcionService solicitudes = Mockito.mock(SolicitudAdopcionService.class);
    private final UsuarioService usuarios = Mockito.mock(UsuarioService.class);
    private MockMvc mvc;

    @BeforeEach
    void configurar() {
        mvc = MockMvcBuilders.standaloneSetup(new SolicitudAdopcionController(solicitudes), new AuthController(usuarios))
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void loginConCredencialesIncorrectasResponde401() throws Exception {
        when(usuarios.iniciarSesion(any())).thenThrow(new CredencialesInvalidasException("Correo o contraseña incorrectos"));

        mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"correo\":\"ana@correo.com\",\"contrasena\":\"x\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.mensaje").value("Correo o contraseña incorrectos"));
    }

    @Test
    void cambiarEstadoSinEstadoResponde400SinLlamarAlServicio() throws Exception {
        mvc.perform(patch("/api/solicitudes-adopcion/5/estado").contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuarioId\":40}"))
                .andExpect(status().isBadRequest());

        verify(solicitudes, never()).cambiarEstado(any(), any());
    }

    @Test
    void decidirSolicitudDeOtraFundacionResponde403() throws Exception {
        when(solicitudes.cambiarEstado(eq(5L), any())).thenThrow(new AccesoDenegadoException("No"));

        mvc.perform(patch("/api/solicitudes-adopcion/5/estado").contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuarioId\":41,\"estado\":\"APROBADA\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void listarPorFundacionSinEstadoTraeTodas() throws Exception {
        when(solicitudes.listarPorFundacion(19L)).thenReturn(List.of());

        mvc.perform(get("/api/solicitudes-adopcion/por-fundacion").param("fundacionId", "19"))
                .andExpect(status().isOk());

        verify(solicitudes).listarPorFundacion(19L);
    }
}
