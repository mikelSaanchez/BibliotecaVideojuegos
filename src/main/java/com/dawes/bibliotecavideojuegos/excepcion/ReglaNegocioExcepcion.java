package com.dawes.bibliotecavideojuegos.excepcion;

public class ReglaNegocioExcepcion extends RuntimeException {
    public ReglaNegocioExcepcion(String message) {
        super(message);
    }
}
