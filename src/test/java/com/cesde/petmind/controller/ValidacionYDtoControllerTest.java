package com.cesde.petmind.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.cesde.petmind.dto.ContactoDto;
import com.cesde.petmind.dto.FundacionResponse;
import com.cesde.petmind.dto.UsuarioResponse;
import com.cesde.petmind.dto.UsuarioRequest;
import com.cesde.petmind.exceptions.GlobalExceptionHandler;
import com.cesde.petmind.model.enums.EstadoVerificacion;
import com.cesde.petmind.model.enums.RolUsuario;
import com.cesde.petmind.service.FundacionService;
import com.cesde.petmind.service.MascotaService;
import com.cesde.petmind.service.UsuarioService;

class ValidacionYDtoControllerTest {
    private final UsuarioService usuarios = Mockito.mock(UsuarioService.class);
    private final FundacionService fundaciones = Mockito.mock(FundacionService.class);
    private final MascotaService mascotas = Mockito.mock(MascotaService.class);
    private MockMvc mvc;

    @BeforeEach
    void configurar() {
        mvc = MockMvcBuilders.standaloneSetup(new UsuarioController(usuarios),
                new FundacionController(fundaciones), new MascotaController(mascotas))
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void usuarioInvalidoMuestraCamposAnidadosYNoLlamaServicio() throws Exception {
        mvc.perform(post("/api/usuarios").contentType(MediaType.APPLICATION_JSON).content("""
                {"nombre":"", "apellido":"Gómez", "rol":"ADOPTANTE", "contacto":{"emailContacto":""}}
                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje", containsString("nombre: El nombre es obligatorio")))
                .andExpect(jsonPath("$.mensaje", containsString("contacto.emailContacto: El correo es obligatorio")));
        verify(usuarios, never()).crear(any());
    }

    @Test
    void usuarioValidoDevuelveIdsSinContrasenaNiToken() throws Exception {
        when(usuarios.crear(any())).thenReturn(new UsuarioResponse(7L, 9L, "Ana", "Gómez",
                new ContactoDto(null, null, "ana@correo.com"), null, null,
                RolUsuario.REPRESENTANTE_FUNDACION, false, true, null, null));
        mvc.perform(post("/api/usuarios").contentType(MediaType.APPLICATION_JSON).content("""
                {"fundacionId":9, "nombre":"Ana", "apellido":"Gómez", "contrasena":"Clave#123",
                 "contacto":{"emailContacto":"ana@correo.com"}, "rol":"REPRESENTANTE_FUNDACION"}
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.fundacionId").value(9))
                .andExpect(jsonPath("$.contrasena").doesNotExist())
                .andExpect(jsonPath("$.tokenCodigo").doesNotExist());
        ArgumentCaptor<UsuarioRequest> captor = ArgumentCaptor.forClass(UsuarioRequest.class);
        verify(usuarios).crear(captor.capture());
        assertEquals("Clave#123", captor.getValue().contrasena());
        assertEquals(9L, captor.getValue().fundacionId());
    }

    @Test
    void fundacionValidaDevuelveIdEnActualizacion() throws Exception {
        when(fundaciones.actualizar(any(), any())).thenReturn(new FundacionResponse(3L, "Huellas", "123",
                "Ana", null, null, new ContactoDto(null, null, "info@huellas.co"),
                null, null, null, null, EstadoVerificacion.PENDIENTE, null, null, null, true, null, null));
        mvc.perform(put("/api/fundaciones/3").contentType(MediaType.APPLICATION_JSON).content("""
                {"nombre":"Huellas", "nit":"123", "representanteLegal":"Ana",
                 "contacto":{"emailContacto":"info@huellas.co"}, "estadoVerificacion":"PENDIENTE"}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void fundacionInvalidaInformaCamposSinLlamarServicio() throws Exception {
        mvc.perform(post("/api/fundaciones").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje", containsString("nit: El NIT es obligatorio")))
                .andExpect(jsonPath("$.mensaje", containsString("contacto: El contacto es obligatorio")));
        verify(fundaciones, never()).crear(any());
    }

    @Test
    void handlerTambienMuestraValidacionesDeDtosExistentes() throws Exception {
        mvc.perform(post("/api/mascotas").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje", containsString("nombre: El nombre es obligatorio")))
                .andExpect(jsonPath("$.mensaje", containsString("fundacionId:")));
        verify(mascotas, never()).crear(any());
    }
}
