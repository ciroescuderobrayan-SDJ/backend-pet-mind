package com.cesde.petmind.exceptions;

// Correo o contrasena incorrectos al iniciar sesion: responde 401.
public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}
