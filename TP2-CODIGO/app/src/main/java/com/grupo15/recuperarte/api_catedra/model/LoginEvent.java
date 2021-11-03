package com.grupo15.recuperarte.api_catedra.model;

import com.grupo15.recuperarte.api_catedra.api.IRegistrable;

public class LoginEvent implements IRegistrable {
    private final String email;
    public LoginEvent(String email) { this.email = email; }
    @Override public EventType type() { return EventType.LOGIN; }
    @Override public String description() { return String.format("%s se logueo", email); }
}
