package com.grupo15.recuperarte.api_catedra.http;

public class HttpException extends Exception {
    public HttpException() { super("error en la conexion HTTP"); }
    public HttpException(String message) { super(message); }
    public HttpException(String message, Throwable cause) { super(message, cause); }
}
