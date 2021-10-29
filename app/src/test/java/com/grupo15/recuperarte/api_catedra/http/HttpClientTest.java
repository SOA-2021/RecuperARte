package com.grupo15.recuperarte.api_catedra.http;

import com.grupo15.recuperarte.api_catedra.User;
import com.grupo15.recuperarte.api_catedra.request.RegisterRequest;
import com.grupo15.recuperarte.api_catedra.response.TokenResponse;

import org.junit.Assert;
import org.junit.Test;

public class HttpClientTest {
    /* Simple POST test */
    @Test
    public void test() throws Exception {
        User u = new User("test", "test", 41523523, "mail@mail.com");
        RegisterRequest r = new RegisterRequest(u, "12345678");
        TokenResponse res = HttpClient.doPost(
                "http://so-unlam.net.ar/api/api/register",
                r, TokenResponse.class
        );

        Assert.assertNotNull(res);
    }
}