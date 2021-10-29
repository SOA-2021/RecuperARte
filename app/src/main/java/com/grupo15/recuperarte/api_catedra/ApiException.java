package com.grupo15.recuperarte.api_catedra;

/**
 * Representa una exception en la comunicacion con la API de la catedra.
 */
public class ApiException extends Exception {
    public ApiException() {
        super("Ha ocurrido un error al comunicarse con la API de la catedra");
    }

    public ApiException(String message) { super(message); }
    public ApiException(String message, Throwable cause) { super(message, cause); }
}