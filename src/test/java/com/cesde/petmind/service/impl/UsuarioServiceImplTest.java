package com.cesde.petmind.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cesde.petmind.exceptions.RecursoDuplicadoException;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.dto.ContactoDto;
import com.cesde.petmind.dto.UsuarioRequest;
import com.cesde.petmind.dto.UsuarioResponse;
import com.cesde.petmind.model.entity.Fundacion;
import com.cesde.petmind.model.embeddable.InformacionContacto;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.model.enums.EstadoVerificacion;
import com.cesde.petmind.model.enums.RolUsuario;
import com.cesde.petmind.repository.FundacionRepository;
import com.cesde.petmind.repository.UsuarioRepository;

// Pruebas unitarias de las reglas de usuarios: contrasena, correo unico y rol ADMIN.
// Usan Mockito en lugar de la base de datos, por eso no necesitan el .env de Neon.
@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    private static final String MENSAJE =
            "La contrasena debe tener minimo 8 caracteres, una mayuscula y un caracter especial";

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private FundacionRepository fundacionRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuarioConContrasena(String contrasena) {
        InformacionContacto contacto = new InformacionContacto();
        contacto.setEmailContacto("prueba@correo.com");
        return Usuario.builder()
                .nombre("Ana")
                .apellido("Gomez")
                .contrasena(contrasena)
                .contacto(contacto)
                .rol(RolUsuario.ADOPTANTE)
                .build();
    }

    private UsuarioRequest request(Usuario usuario) {
        return new UsuarioRequest(usuario.getFundacion() == null ? null : usuario.getFundacion().getId(),
                usuario.getNombre(), usuario.getApellido(), usuario.getContrasena(),
                ContactoDto.desde(usuario.getContacto()), null, usuario.getUrlAvatar(), usuario.getRol());
    }

    @ParameterizedTest
    @ValueSource(strings = { "Petmind#1", "Clave$egura", "ÑandúFeliz!" })
    void crearAceptaContrasenaQueCumpleLaRegla(String contrasena) {
        Usuario usuario = usuarioConContrasena(contrasena);
        when(usuarioRepository.existsByContactoEmailContactoIgnoreCase("prueba@correo.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer((invocacion) -> invocacion.getArgument(0));

        assertEquals("Ana", usuarioService.crear(request(usuario)).nombre());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "Pet#1",        // menos de 8 caracteres
            "petmind#2026", // sin mayuscula
            "Petmind2026",  // sin caracter especial
            "Petmind 2026"  // un espacio no cuenta como caracter especial
    })
    void crearRechazaContrasenaQueNoCumpleLaRegla(String contrasena) {
        Usuario usuario = usuarioConContrasena(contrasena);

        ReglaDeNegocioException error =
                assertThrows(ReglaDeNegocioException.class, () -> usuarioService.crear(request(usuario)));

        assertEquals(MENSAJE, error.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void crearValidaLaContrasenaAntesQueElCorreo() {
        // Si la contrasena es invalida no se consulta la base para revisar el correo.
        assertThrows(ReglaDeNegocioException.class, () -> usuarioService.crear(request(usuarioConContrasena("corta"))));

        verify(usuarioRepository, never()).existsByContactoEmailContactoIgnoreCase(any());
    }

    @Test
    void crearGuardaElCorreoEnMinusculasYSinEspacios() {
        Usuario usuario = usuarioConContrasena("Petmind#1");
        usuario.getContacto().setEmailContacto("  Ana.Gomez@Correo.COM ");
        when(usuarioRepository.existsByContactoEmailContactoIgnoreCase("ana.gomez@correo.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer((invocacion) -> invocacion.getArgument(0));

        assertEquals("ana.gomez@correo.com", usuarioService.crear(request(usuario)).contacto().emailContacto());
    }

    @Test
    void crearRechazaCorreoRepetidoAunqueCambienLasMayusculas() {
        Usuario usuario = usuarioConContrasena("Petmind#1");
        usuario.getContacto().setEmailContacto("PRUEBA@correo.com");
        when(usuarioRepository.existsByContactoEmailContactoIgnoreCase("prueba@correo.com")).thenReturn(true);

        assertThrows(RecursoDuplicadoException.class, () -> usuarioService.crear(request(usuario)));
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void actualizarNoPermiteVolverseAdmin() {
        Usuario existente = usuarioConContrasena("Petmind#1");
        existente.setId(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        Usuario cambios = usuarioConContrasena(null);
        cambios.setRol(RolUsuario.ADMIN);

        ReglaDeNegocioException error =
                assertThrows(ReglaDeNegocioException.class, () -> usuarioService.actualizar(1L, request(cambios)));

        assertEquals("No se puede asignar el rol ADMIN a un usuario", error.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void actualizarNoRevisaElCorreoSiSoloCambianLasMayusculas() {
        Usuario existente = usuarioConContrasena("Petmind#1");
        existente.setId(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer((invocacion) -> invocacion.getArgument(0));
        Usuario cambios = usuarioConContrasena(null);
        cambios.getContacto().setEmailContacto("Prueba@Correo.com");

        usuarioService.actualizar(1L, request(cambios));

        verify(usuarioRepository, never()).existsByContactoEmailContactoIgnoreCase(any());
    }

    @Test
    void representanteDevuelveIdsYResuelveFundacionVerificada() {
        Fundacion fundacion = new Fundacion();
        fundacion.setId(9L);
        fundacion.setEstadoVerificacion(EstadoVerificacion.VERIFICADA);
        Usuario usuario = usuarioConContrasena("Petmind#1");
        usuario.setRol(RolUsuario.REPRESENTANTE_FUNDACION);
        usuario.setFundacion(fundacion);
        when(fundacionRepository.findById(9L)).thenReturn(Optional.of(fundacion));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocacion -> {
            Usuario guardado = invocacion.getArgument(0);
            assertEquals(fundacion, guardado.getFundacion());
            guardado.setId(7L);
            return guardado;
        });

        UsuarioResponse respuesta = usuarioService.crear(request(usuario));

        assertEquals(7L, respuesta.id());
        assertEquals(9L, respuesta.fundacionId());
    }

    @Test
    void actualizarConservaContrasenaYVerificacion() {
        Usuario existente = usuarioConContrasena("Petmind#1");
        existente.setId(1L);
        existente.setCorreoVerificado(true);
        existente.setTokenCodigo("token-interno");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocacion -> invocacion.getArgument(0));
        Usuario cambios = usuarioConContrasena("OtraClave#1");

        UsuarioResponse respuesta = usuarioService.actualizar(1L, request(cambios));

        assertEquals("Petmind#1", existente.getContrasena());
        assertEquals("token-interno", existente.getTokenCodigo());
        assertEquals(true, respuesta.correoVerificado());
        assertNull(respuesta.fundacionId());
    }
}
