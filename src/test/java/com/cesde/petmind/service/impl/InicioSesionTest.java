package com.cesde.petmind.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cesde.petmind.dto.ContactoDto;
import com.cesde.petmind.dto.LoginRequest;
import com.cesde.petmind.dto.UsuarioRequest;
import com.cesde.petmind.exceptions.CredencialesInvalidasException;
import com.cesde.petmind.model.embeddable.InformacionContacto;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.model.enums.RolUsuario;
import com.cesde.petmind.repository.FundacionRepository;
import com.cesde.petmind.repository.UsuarioRepository;

// Inicio de sesion y contrasenas cifradas con BCrypt.
@ExtendWith(MockitoExtension.class)
class InicioSesionTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private FundacionRepository fundacionRepository;
    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private static Usuario usuarioCon(String contrasena) {
        InformacionContacto contacto = new InformacionContacto();
        contacto.setEmailContacto("ana@correo.com");
        Usuario usuario = Usuario.builder().nombre("Ana").apellido("Gómez").contrasena(contrasena)
                .contacto(contacto).rol(RolUsuario.ADOPTANTE).build();
        usuario.setId(7L);
        return usuario;
    }

    @Test
    void crearGuardaLaContrasenaCifrada() {
        when(usuarioRepository.existsByContactoEmailContactoIgnoreCase("ana@correo.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocacion -> invocacion.getArgument(0));
        ContactoDto contacto = new ContactoDto(null, null, "ana@correo.com");

        usuarioService.crear(new UsuarioRequest(null, "Ana", "Gómez", "Petmind#1", contacto, null, null, RolUsuario.ADOPTANTE));

        ArgumentCaptor<Usuario> guardado = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(guardado.capture());
        assertNotEquals("Petmind#1", guardado.getValue().getContrasena());
        assertTrue(new BCryptPasswordEncoder().matches("Petmind#1", guardado.getValue().getContrasena()));
    }

    @Test
    void iniciarSesionConContrasenaCifradaDevuelveElUsuario() {
        Usuario ana = usuarioCon(new BCryptPasswordEncoder().encode("Petmind#1"));
        when(usuarioRepository.findFirstByContactoEmailContactoIgnoreCaseAndEstadoActivoTrue("ana@correo.com"))
                .thenReturn(Optional.of(ana));

        assertEquals(7L, usuarioService.iniciarSesion(new LoginRequest(" Ana@Correo.com ", "Petmind#1")).id());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void unaCuentaAntiguaEnTextoPlanoSeCifraAlIniciarSesion() {
        Usuario ana = usuarioCon("Petmind#1");
        when(usuarioRepository.findFirstByContactoEmailContactoIgnoreCaseAndEstadoActivoTrue("ana@correo.com"))
                .thenReturn(Optional.of(ana));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        usuarioService.iniciarSesion(new LoginRequest("ana@correo.com", "Petmind#1"));

        assertTrue(new BCryptPasswordEncoder().matches("Petmind#1", ana.getContrasena()));
    }

    @Test
    void contrasenaIncorrectaOCorreoDesconocidoNoInicianSesion() {
        when(usuarioRepository.findFirstByContactoEmailContactoIgnoreCaseAndEstadoActivoTrue("ana@correo.com"))
                .thenReturn(Optional.of(usuarioCon(new BCryptPasswordEncoder().encode("Petmind#1"))));
        when(usuarioRepository.findFirstByContactoEmailContactoIgnoreCaseAndEstadoActivoTrue("otro@correo.com"))
                .thenReturn(Optional.empty());

        assertThrows(CredencialesInvalidasException.class,
                () -> usuarioService.iniciarSesion(new LoginRequest("ana@correo.com", "Otra#clave1")));
        assertThrows(CredencialesInvalidasException.class,
                () -> usuarioService.iniciarSesion(new LoginRequest("otro@correo.com", "Petmind#1")));
    }
}
