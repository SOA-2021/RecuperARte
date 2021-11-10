package com.grupo15.recuperarte.presenter;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;

import com.google.zxing.integration.android.IntentResult;
import com.grupo15.recuperarte.api_catedra.api.ApiException;
import com.grupo15.recuperarte.api_catedra.api.IApiClient;
import com.grupo15.recuperarte.global.Conf;
import com.grupo15.recuperarte.mvp.ILogin;

public class LoginPresenter implements ILogin.Presenter {
    private static final String QR_CODE = "123456789";

    private final ILogin.View view;
    private final Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

    public LoginPresenter(ILogin.View view) { this.view = view; }

    @Override
    public void login(String email, String password) {
        new Thread(()-> {
            Conf c = Conf.getInstance();
            IApiClient api = c.apiClient();
            try {
                api.login(email, password);

                /* Una vez que se ha logueado correctamente, inicio el escaneo QR */
                handler.post(this.view::initiateScan);
            } catch ( ApiException e ) {
                String errorMessage = String.format("Error: %s", e.getMessage());
                handler.post(() -> this.view.onLoginError(errorMessage));
            }
        }).start();
    }

    @Override
    public void readQR(@NonNull IntentResult result) {
        /* Si no se ha leido nada */
        if (result.getContents() == null) {
            handler.post(()->this.view.onLoginError("Lectura cancelada"));
            return;
        }

        /* Si el codigo que pone es incorrecto */
        if (!result.getContents().equals(QR_CODE)) {
            handler.post(()->this.view.onLoginError("Codigo incorrecto"));
            return;
        }

        handler.post(this.view::onLoginSuccess);
    }
}
