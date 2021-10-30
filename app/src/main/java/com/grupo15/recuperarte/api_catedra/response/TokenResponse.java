package com.grupo15.recuperarte.api_catedra.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.grupo15.recuperarte.api_catedra.model.Token;

public class TokenResponse {
    @Expose
    private boolean success;
    @Expose
    private String env;
    @Expose
    private String token;
    @Expose
    @SerializedName("token_refresh")
    private String refresh;

    public Token token() { return new Token(this.token, this.refresh); }
    public boolean success() { return this.success; }
}
