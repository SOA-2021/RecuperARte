package com.grupo15.recuperarte.api_catedra.response;

import com.google.gson.annotations.Expose;

public class ErrorResponse {
    @Expose
    private boolean success;
    @Expose
    private String env;
    @Expose
    private String msg;

    public String message() { return this.msg; }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "success=" + success +
                ", env='" + env + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
