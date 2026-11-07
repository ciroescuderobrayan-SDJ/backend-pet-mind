package com.cesde.petmind.exceptions;

public class AnaliticaException extends RuntimeException {

    public AnaliticaException(String mensaje) {
        super(mensaje);
    }

    public AnaliticaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
