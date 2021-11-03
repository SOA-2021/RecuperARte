package com.grupo15.recuperarte.global;

import com.grupo15.recuperarte.api_catedra.api.ApiClient;
import com.grupo15.recuperarte.api_catedra.api.IApiClient;
import com.grupo15.recuperarte.api_catedra.http.HttpClient;
import com.grupo15.recuperarte.api_catedra.http.IHttpClient;

public final class Conf {
    public static final String ENV = "PROD";
    private static Conf singleton;
    private final IApiClient apiClient;

    private Conf() {
        IHttpClient http = new HttpClient();
        this.apiClient = new ApiClient(http);
    }

    public static synchronized Conf getInstance() {
        if ( singleton == null )
            singleton = new Conf();

        return singleton;
    }

    public IApiClient apiClient() { return this.apiClient; }
}