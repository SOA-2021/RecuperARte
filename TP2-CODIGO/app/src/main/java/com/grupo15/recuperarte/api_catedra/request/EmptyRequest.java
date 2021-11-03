package com.grupo15.recuperarte.api_catedra.request;

/**
 * Representa una request con el cuerpo vacio.
 */
public class EmptyRequest implements IApiRequest {
    @Override
    public String toJson() { return ""; }
}
