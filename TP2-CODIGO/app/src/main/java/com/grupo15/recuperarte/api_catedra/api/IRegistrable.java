package com.grupo15.recuperarte.api_catedra.api;

import com.grupo15.recuperarte.api_catedra.model.EventType;

public interface IRegistrable {
    /**
     * Devuelve el tipo de evento a registrar.
     */
    public EventType type();

    /**
     * Devuelve la descripcion del registrable
     */
    public String description();
}