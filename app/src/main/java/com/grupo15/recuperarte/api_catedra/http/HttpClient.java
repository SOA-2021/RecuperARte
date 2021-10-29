package com.grupo15.recuperarte.api_catedra.http;

import android.renderscript.ScriptGroup;

import com.google.gson.Gson;
import com.grupo15.recuperarte.api_catedra.request.IApiRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient {

    public static <T>T doGet(String url, IApiRequest req, Class<T> responseClass)
        throws HttpException {
        return doGet(url, null, req, responseClass);
    }

    public static <T>T doGet(
            String url,
            String token,
            IApiRequest req,
            Class<T> responseClass
    ) throws HttpException {
        /* make initial connection */
        final HttpURLConnection con;
        try {
            URL uri = new URL(url);
            con = (HttpURLConnection) uri.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            /* if token was provided, set Authorization */
            if ( token != null )
                con.setRequestProperty("Authorization", String.format("Bearer %s", token));
        } catch ( IOException e ) {
            final String msg = String.format("no se pudo abrir la conexion con %s: %s",
                    url, e.getMessage());
            throw new HttpException(msg, e);
        }

        /* write body */
        try (OutputStream out = con.getOutputStream() ) {
            final byte[] body = req.toJson().getBytes(StandardCharsets.UTF_8);
            out.write(body, 0, body.length);
        } catch ( IOException e ) {
            final String msg = String.format("no se pudo escribir el body de la request: %s",
                    e.getMessage());
            throw new HttpException(msg, e);
        }

        /* read response */
        try {
            final InputStream ir = con.getInputStream();
            final InputStreamReader isr = new InputStreamReader(ir, StandardCharsets.UTF_8);
            final BufferedReader br = new BufferedReader(isr);
            final StringBuilder strb = new StringBuilder();
            String line;
            while ( (line = br.readLine()) != null )
                strb.append(line);

            return new Gson().fromJson(strb.toString(), responseClass);
        } catch ( IOException e ) {
            final String msg = String.format("no se pudo leer la respuesta: %s", e.getMessage());
            throw new HttpException(msg, e);
        }
    }
}
