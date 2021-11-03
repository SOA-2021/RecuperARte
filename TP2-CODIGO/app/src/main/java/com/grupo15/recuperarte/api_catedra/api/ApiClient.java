package com.grupo15.recuperarte.api_catedra.api;

import androidx.annotation.NonNull;

import com.grupo15.recuperarte.api_catedra.http.HttpException;
import com.grupo15.recuperarte.api_catedra.http.IHttpClient;
import com.grupo15.recuperarte.api_catedra.model.LoginEvent;
import com.grupo15.recuperarte.api_catedra.model.Token;
import com.grupo15.recuperarte.api_catedra.model.User;
import com.grupo15.recuperarte.api_catedra.request.EmptyRequest;
import com.grupo15.recuperarte.api_catedra.request.EventRequest;
import com.grupo15.recuperarte.api_catedra.request.IApiRequest;
import com.grupo15.recuperarte.api_catedra.request.LoginRequest;
import com.grupo15.recuperarte.api_catedra.request.RegisterRequest;
import com.grupo15.recuperarte.api_catedra.response.EventResponse;
import com.grupo15.recuperarte.api_catedra.response.TokenResponse;

public class ApiClient implements IApiClient {
    private static final String URL_BASE  = "http://so-unlam.net.ar/api/api/";
    private static final String URL_REG   = URL_BASE + "register";
    private static final String URL_LOGIN = URL_BASE + "login";
    private static final String URL_TOK   = URL_BASE + "refresh";
    private static final String URL_EVT   = URL_BASE + "event";

    private Token token;
    private @NonNull final IHttpClient http;

    public ApiClient(@NonNull IHttpClient http) { this.http = http; }

    @Override
    public Token registerUser(User newUser, String password) throws ApiException {
        IApiRequest req = new RegisterRequest(newUser, password);
        try {
            TokenResponse res = http.doPost(URL_REG, req, TokenResponse.class);
            if ( !res.success() )
                throw new ApiException("error inesperado al obtener token");

            Token tok = res.token();
            this.token = tok;
            return tok;
        } catch ( HttpException e ) {
            final String msg = String.format("error al registrar usuario %s: %s",
                    newUser.toString(), e.getMessage());
            throw new ApiException(msg, e);
        }
    }

    @Override
    public Token login(String email, String password) throws ApiException {
        IApiRequest req = new LoginRequest(email, password);
        try {
            TokenResponse res = http.doPost(URL_LOGIN, req, TokenResponse.class);
            if ( !res.success() )
                throw new ApiException("error inesperado al obtener token");

            Token tok = res.token();
            this.token = tok;

            /* registro el evento de login in */
            register(new LoginEvent(email));

            return tok;
        } catch ( HttpException e ) {
            final String msg = String.format("error al loguear usuario %s: %s", email,
                    e.getMessage());
            throw new ApiException(msg, e);
        }
    }

    @Override
    public Token renewToken() throws ApiException {
        IApiRequest req = new EmptyRequest();
        try {
            TokenResponse res = http.doPut(URL_TOK, this.token.refresh(), req, TokenResponse.class);
            if ( !res.success() )
                throw new ApiException("error inesperado al obtener token");

            Token tok = res.token();
            this.token = tok;
            return tok;
        } catch ( HttpException e ) {
            final String msg = String.format("error al renovar token: %s", e.getMessage());
            throw new ApiException(msg, e);
        }
    }

    @Override
    public void register(IRegistrable r) throws ApiException {
        if ( this.token.needsRefresh() )
            this.renewToken();

        IApiRequest req = new EventRequest(r);
        try {
            EventResponse res = http.doPost(URL_EVT, this.token.toString(), req, EventResponse.class);
            if ( !res.success() )
                throw new ApiException("error inesperado al registrar evento");
        } catch ( HttpException e ) {
            final String msg = String.format("error al renovar token: %s", e.getMessage());
            throw new ApiException(msg, e);
        }
    }
}
