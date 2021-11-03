package com.grupo15.recuperarte.api_catedra.http;

import com.grupo15.recuperarte.api_catedra.request.IApiRequest;

public interface IHttpClient {
    <T> T doPut(String url, String token, IApiRequest req, Class<T> responseClass)
            throws HttpException;

    <T> T doPost(String url, IApiRequest req, Class<T> responseClass)
            throws HttpException;

    <T> T doPost(String url, String token, IApiRequest req, Class<T> responseClass)
            throws HttpException;
}
