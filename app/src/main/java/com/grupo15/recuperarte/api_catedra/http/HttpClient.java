package com.grupo15.recuperarte.api_catedra.http;

import com.google.gson.Gson;
import com.grupo15.recuperarte.api_catedra.request.IApiRequest;
import com.grupo15.recuperarte.api_catedra.response.ErrorResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient implements IHttpClient {

    /**
     * Realiza un PUT.
     * @param url la url a la cual golpear
     * @param token el token con el cual realizar la autenticacion.
     * @param req la request a enviar.
     * @param responseClass el tipo de clase de respuesta esperada. Por ejemplo,
     *                      {@link com.grupo15.recuperarte.api_catedra.response.TokenResponse}
     * @param <T> igual que responseClass
     * @return la respuesta dada por la API.
     * @throws HttpException cuando hay un error de conexion, o cuando la respuesta lleva un estado
     *                       de error (mayor a 300)
     */
    @Override
    public <T>T doPut(String url, String token, IApiRequest req, Class<T> responseClass)
        throws HttpException {
        return doRequest(url, token, "PUT", req, responseClass);
    }

    /**
     * Similar a {@link #doPut(String, String, IApiRequest, Class)} salvo que realiza un POST
     */
    @Override
    public <T>T doPost(String url, IApiRequest req, Class<T> responseClass)
            throws HttpException {
        return doPost(url, null,  req, responseClass);
    }

    /**
     * Similar a {@link #doPost(String, IApiRequest, Class)} pero usa token de autenticacion.
     */
    @Override
    public <T>T doPost(String url, String token, IApiRequest req, Class<T> responseClass)
        throws HttpException {
        return doRequest(url, token, "POST", req, responseClass);
    }

    private <T>T doRequest(
            String url,
            String token,
            String method,
            IApiRequest req,
            Class<T> responseClass
    ) throws HttpException {
        /* make initial connection */
        final HttpURLConnection con;
        try {
            URL uri = new URL(url);
            con = (HttpURLConnection) uri.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            /* if token was provided, set Authorization */
            if ( token != null )
                con.setRequestProperty("Authorization", String.format("Bearer %s", token));
        } catch ( IOException e ) {
            final String msg = String.format("no se pudo abrir la conexion con %s: %s",
                    url, e.getMessage());
            throw new HttpException(msg, e);
        }

        /* write body */
        con.setDoOutput(true);
        try (OutputStream out = con.getOutputStream() ) {
            final byte[] body = req.toJson().getBytes(StandardCharsets.UTF_8);
            out.write(body, 0, body.length);
        } catch ( IOException e ) {
            final String msg = String.format("no se pudo escribir el body de la request: %s",
                    e.getMessage());
            throw new HttpException(msg, e);
        }

        /* read status code. If error, then throw exception */
        try {
            int status = con.getResponseCode();
            if ( status > 299 ) {
                final InputStreamReader reader = new InputStreamReader(con.getErrorStream(),
                        StandardCharsets.UTF_8);
                ErrorResponse err = new Gson().fromJson(reader, ErrorResponse.class);

                /* primero logueo mi request y el mensaje completo de error */
                String errmsg = String.format("Req: %s\nResp: %s", req.toJson(), err.toString());
                System.err.println(errmsg);

                throw new HttpException(
                        String.format("error en la comunicacion con la API: %s", err.message()));
            }
        } catch ( IOException e ) {
            final String msg = String.format("error leyendo el estado de la respuesta: %s",
                    e.getMessage());
            throw new HttpException(msg, e);
        }

        /* read response */
        try {
            final InputStream ir = con.getInputStream();
            final InputStreamReader isr = new InputStreamReader(ir, StandardCharsets.UTF_8);
            return new Gson().fromJson(isr, responseClass);
        } catch ( IOException e ) {
            final String msg = String.format("no se pudo leer la respuesta: %s", e.getMessage());
            throw new HttpException(msg, e);
        }
    }
}
