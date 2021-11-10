package com.grupo15.recuperarte.mvp;

import com.google.zxing.integration.android.IntentResult;

public interface ILogin {
    interface Presenter {
        /**
         * Realiza el login pegandole a la API.
         */
        void login(String email, String password);

        /**
         * Lee los resultados de la lectura del codigo QR
         */
        void readQR(IntentResult result);
    }
    interface View {
        /**
         * Comienza el escaneo del codigo QR.
         * Debe realizarse cuando el login es exitoso.
         */
        void initiateScan();

        /**
         * En caso de error en el login, ya sea por la API o por la lectura del codigo QR.
         */
        void onLoginError(String errorMessage);

        /**
         * Login exitoso.
         * Se llama una vez finalizada la lectura del QR.
         */
        void onLoginSuccess();
    }
}
