package com.grupo15.recuperarte.api_catedra.api;

import com.grupo15.recuperarte.api_catedra.model.Token;
import com.grupo15.recuperarte.api_catedra.model.User;

public interface IApiClient {
    /**
     * Registra un nuevo usuario.
     * @param newUser el nuevo usuario a registrar.
     * @return el token con el cual se debe hacer el resto de las comunicaciones.
     * @throws ApiException cuando sucede un error en la comunicacion con la API. Este error se
     *                      puede deber a que el dispositivo no tiene Internet, o el servidor
     *                      retorna un error.
     */
    Token registerUser(User newUser) throws ApiException;

    /**
     * Realiza el login de un usuario.
     * @param email el correo electronico del usuario.
     * @param password la contraseña ingresada por el usuario.
     * @return el token con el cual se debe hacer el resto de las comunicaciones.
     * @throws ApiException cuando sucede un error en la comunicacion con la API. Igual que
     *                      {@link #registerUser(User)}
     */
    Token login(String email, String password) throws ApiException;

    /**
     * Se renueva el periodo del token.
     * @param t el token que se quiere renovar.
     * @return el token renovado. El anterior token ya no es de utilidad.
     * @throws ApiException Igual que {@link #registerUser(User)}
     */
    Token renewToken(Token t) throws ApiException;

    /**
     * Registra un evento en el servidor.
     * @param r un elemento registrable. Por ejemplo, un evento de algun sensor que se quiera 
     *          registrar.
     * @throws ApiException Igual que {@link #registerUser(User)}
     */
    void register(IRegistrable r) throws ApiException;
}