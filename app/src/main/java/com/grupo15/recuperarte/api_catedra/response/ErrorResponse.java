package com.grupo15.recuperarte.api_catedra.response;

import com.google.gson.annotations.Expose;

public class ErrorResponse {
    @Expose
    private boolean success;
    @Expose
    private String env;
    @Expose
    private String msg;
}
