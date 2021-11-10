package com.grupo15.recuperarte.mvp;

public interface ISignUp {
    interface Presenter {
        /**
         * Registra un usuario utilizando la API.
         */
        void registerUser(String name, String lastname, String dni, String email, String psw);
    }
    interface View {
        /**
         * En caso de que el registro sea exitoso, ejecuta este metodo.
         */
        void registerOnSuccess();

        /**
         * En caso de que el registro falle, ejecuta este metodo.
         * Muestra un mensaje de error con un Toast.
         * @param errorMessage el mensaje a mostrar.
         */
        void registerOnError(String errorMessage);
    }
}
