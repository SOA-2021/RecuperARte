package com.grupo15.recuperarte.api_catedra;

import com.google.gson.annotations.Expose;

public class User {
    /**
     * El nombre del usuario.
     */
    @Expose
    private String name;

    /**
     * El apellido del usuario.
     */
    @Expose
    private String lastname;

    /**
     * El documento del usuario.
     */
    @Expose
    private String dni;

    /**
     * El correo electronico del usuario.
     */
    @Expose
    private String email;

    public User(String name, String lastname, String dni, String email) {
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.email = email;
    }

    public String name() { return this.name; }
    public String lastname() { return this.lastname; }
    public String dni() { return this.dni; }
    public String email() { return this.email; }
}