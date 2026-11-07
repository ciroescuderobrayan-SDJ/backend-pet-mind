package com.cesde.petmind.exceptions;

// El usuario existe pero no puede hacer esa accion (p. ej. decidir una solicitud de otra fundacion): responde 403.
public class AccesoDenegadoException extends RuntimeException {

    public AccesoDenegadoException(String mensaje) {
        super(mensaje);
    }
}
