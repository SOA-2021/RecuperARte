package com.grupo15.recuperarte.api_catedra.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
}
