package com.grupo15.recuperarte.api_catedra.request;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.grupo15.recuperarte.api_catedra.model.User;
import com.grupo15.recuperarte.global.Conf;

public class RegisterRequest implements IApiRequest {
    @Expose
    public String env;
    @Expose
    public String name;
    @Expose
    public String lastname;
    @Expose
    public Integer dni;
    @Expose
    public String email;
    @Expose
    public String password;
    @Expose
    private final int commission = 3900;
    @Expose
    private final int group = 15;

    /**
     * Crea una request a partir del usuario a registrar.
     */
    public RegisterRequest(User user, String password) {
      this.env = Conf.ENV;
      this.name = user.name();
      this.lastname = user.lastname();
      this.dni = user.dni();
      this.email = user.email();
      this.password = password;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }
}