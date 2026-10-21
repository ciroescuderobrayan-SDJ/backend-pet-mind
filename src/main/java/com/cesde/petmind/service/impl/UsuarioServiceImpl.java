package com.cesde.petmind.service.impl;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import static com.cesde.petmind.service.support.BuscadorEntidades.buscarActivo;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import com.cesde.petmind.exceptions.RecursoDuplicadoException;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.entity.Fundacion;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.model.enums.EstadoVerificacion;
import com.cesde.petmind.model.enums.RolUsuario;
import com.cesde.petmind.repository.FundacionRepository;
import com.cesde.petmind.repository.UsuarioRepository;
import com.cesde.petmind.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final FundacionRepository fundacionRepository;
    // Regla de contrasena de PetMind: minimo 8 caracteres, una mayuscula y un caracter especial.
    // Es la misma regla que valida el frontend en utils/password.js.
    private static final int LONGITUD_MINIMA_CONTRASENA = 8;
    private static final Pattern MAYUSCULA = Pattern.compile("[A-ZÁÉÍÓÚÜÑ]");
    // Caracter especial: cualquier cosa que no sea letra, numero o espacio (!, @, #, $, ...)
    private static final Pattern CARACTER_ESPECIAL = Pattern.compile("[^\\p{L}\\p{N}\\s]");

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findByEstadoActivoTrue();
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        // Un usuario borrado logicamente se trata igual que uno que no existe
        Usuario usuario = buscarActivo(usuarioRepository, id, "un usuario");
        return usuario;
    }

    @Override
    public Usuario crear(Usuario usuario) {
        normalizarCorreo(usuario);
        validarContrasenaSegura(usuario);
        validarRolPermitido(usuario);
        validarCorreoUnico(usuario);
        validarFundacionDelRepresentante(usuario);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizar(Long id, Usuario usuario) {
        Usuario existente = obtenerPorId(id);
        normalizarCorreo(usuario);
        // Por PUT nadie puede volverse ADMIN: antes bastaba enviar "rol": "ADMIN" para obtener el rol
        if (usuario.getRol() == RolUsuario.ADMIN && existente.getRol() != RolUsuario.ADMIN) {
            throw new ReglaDeNegocioException("No se puede asignar el rol ADMIN a un usuario");
        }
        // La regla del correo solo se revisa si el correo cambio
        String correoActual = existente.getContacto().getEmailContacto();
        if (usuario.getContacto() == null || !correoActual.equalsIgnoreCase(usuario.getContacto().getEmailContacto())) {
            validarCorreoUnico(usuario);
        }
        validarFundacionDelRepresentante(usuario);
        // La contrasena, el token y la verificacion del correo no se cambian por aqui
        existente.setNombre(usuario.getNombre());
        existente.setApellido(usuario.getApellido());
        existente.setContacto(usuario.getContacto());
        existente.setDireccion(usuario.getDireccion());
        existente.setUrlAvatar(usuario.getUrlAvatar());
        existente.setRol(usuario.getRol());
        existente.setFundacion(usuario.getFundacion());
        return usuarioRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = obtenerPorId(id);
        // Borrado logico: no se borra la fila, solo se desactiva
        usuario.setEstadoActivo(false);
        usuarioRepository.save(usuario);
    }

    // Regla de negocio 1: la contrasena debe tener minimo 8 caracteres, una mayuscula y un caracter especial
    private void validarContrasenaSegura(Usuario usuario) {
        String contrasena = usuario.getContrasena();
        boolean valida = contrasena != null && contrasena.length() >= LONGITUD_MINIMA_CONTRASENA && MAYUSCULA.matcher(contrasena).find() && CARACTER_ESPECIAL.matcher(contrasena).find();
        if (!valida) {
            throw new ReglaDeNegocioException("La contrasena debe tener minimo 8 caracteres, una mayuscula y un caracter especial");
        }
    }

    // Regla de negocio 2: nadie puede registrarse con rol ADMIN
    private void validarRolPermitido(Usuario usuario) {
        if (usuario.getRol() == RolUsuario.ADMIN) {
            throw new ReglaDeNegocioException("No se puede registrar un usuario con rol ADMIN");
        }
    }

    // Validacion: el correo es obligatorio y no puede estar registrado por otro usuario
    private void validarCorreoUnico(Usuario usuario) {
        if (usuario.getContacto() == null || usuario.getContacto().getEmailContacto() == null) {
            throw new ReglaDeNegocioException("El correo del usuario es obligatorio");
        }
        String correo = usuario.getContacto().getEmailContacto();
        if (usuarioRepository.existsByContactoEmailContactoIgnoreCase(correo)) {
            throw new RecursoDuplicadoException("Ya existe un usuario registrado con el correo " + correo);
        }
    }

    // El correo se guarda sin espacios y en minusculas para que no existan dos cuentas
    // con el mismo correo escrito distinto (ANA@correo.com y ana@correo.com)
    private void normalizarCorreo(Usuario usuario) {
        if (usuario.getContacto() != null && usuario.getContacto().getEmailContacto() != null) {
            usuario.getContacto().setEmailContacto(usuario.getContacto().getEmailContacto().trim().toLowerCase(Locale.ROOT));
        }
    }

    // Validacion: un representante de fundacion debe pertenecer a una fundacion verificada
    private void validarFundacionDelRepresentante(Usuario usuario) {
        // Solo los representantes llevan fundacion; a los demas roles se les quita
        if (usuario.getRol() != RolUsuario.REPRESENTANTE_FUNDACION) {
            usuario.setFundacion(null);
            return;
        }
        if (usuario.getFundacion() == null || usuario.getFundacion().getId() == null) {
            throw new ReglaDeNegocioException("Un representante de fundacion debe indicar la fundacion a la que pertenece");
        }
        Long fundacionId = usuario.getFundacion().getId();
        Fundacion fundacion = buscarActivo(fundacionRepository, fundacionId, "una fundacion");
        if (fundacion.getEstadoVerificacion() != EstadoVerificacion.VERIFICADA) {
            throw new ReglaDeNegocioException("La fundacion " + fundacion.getNombre() + " aun no esta verificada");
        }
        // Se guarda la fundacion completa traida de la base, no solo el id que llego en el JSON
        usuario.setFundacion(fundacion);
    }
    public UsuarioServiceImpl(final UsuarioRepository usuarioRepository, final FundacionRepository fundacionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.fundacionRepository = fundacionRepository;
    }
}
