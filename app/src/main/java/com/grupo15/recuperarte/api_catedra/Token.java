package com.grupo15.recuperarte.api_catedra;

import java.time.LocalDateTime;

/**
 * Representa el token enviado y a enviar a la API de la catedra.
 */
public class Token {
    /**
     * Indica cada cuanto tiempo debe renovarse el token.
     */
    private static final long REFRESH_TIME = 15L;

    /**
     * Representa el token que se recibe en el Response de la API.
     */
    private String token;

    /**
     * Representa el token que hay que enviar para que se renueve el token.
     */
    private String refresh;

    /**
     * La fecha y hora en el cual se ha renovado por ultima vez el token.
     */
    private LocalDateTime datetime;

    /**
     * Crea un token.
     * Recibe el string que representa el token y el string que representa el token a enviar cuando
     * se requiere renovar el mismo.
     */
    public Token(String token, String refresh) {
        this.token = token;
        this.refresh = refresh;
        this.datetime = LocalDateTime.now();
    }

    /**
     * Indica si el token debe renovarse o no.
     */
    public boolean needsRefresh() {
        return datetime.plusMinutes(REFRESH_TIME).isAfter(LocalDateTime.now());
    }
}