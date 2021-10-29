package com.grupo15.recuperarte.api_catedra.request;

public interface IApiRequest {
    /**
     * Transforma la request en un json enviable por HTTP
     */
    public String toJson();
}