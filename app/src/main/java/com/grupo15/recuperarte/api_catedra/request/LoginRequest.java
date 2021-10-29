package com.grupo15.recuperarte.api_catedra.request;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class LoginRequest implements IApiRequest {
    @Expose
    public String email;
    @Expose
    public String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }
}
