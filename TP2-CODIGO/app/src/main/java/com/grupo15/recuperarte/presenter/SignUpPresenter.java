package com.grupo15.recuperarte.presenter;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.grupo15.recuperarte.api_catedra.api.ApiException;
import com.grupo15.recuperarte.api_catedra.api.IApiClient;
import com.grupo15.recuperarte.api_catedra.model.User;
import com.grupo15.recuperarte.global.Conf;
import com.grupo15.recuperarte.mvp.ISignUp;
import com.grupo15.recuperarte.network.NetworkChecker;

public class SignUpPresenter implements ISignUp.Presenter {
    private Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
    private ISignUp.View view;
    private final NetworkChecker network;

    public SignUpPresenter(ISignUp.View view, NetworkChecker network) {
        this.view = view;
        this.network = network;
    }

    @Override
    public void registerUser(String name, String lastname, String dni, String email, String psw) {
        new Thread(()-> {
            if ( !network.isConnected() ) {
                handler.post(() -> this.view.onRegisterError("No esta conectado a Internet"));
                return;
            }

            final Integer dniAsInt;
            try {
                dniAsInt = Integer.parseInt(dni);
            } catch ( NumberFormatException e ) {
                String errorMessage = String.format("El dni debe ser numerico");
                handler.post(()->view.onRegisterError(errorMessage));
                return;
            }

            Conf c = Conf.getInstance();
            IApiClient api = c.apiClient();
            User newUser = new User(name, lastname, dniAsInt, email);
            try {
                api.registerUser(newUser, psw);
                handler.post(view::onRegisterSuccess);
            } catch ( ApiException e ) {
                String errorMessage = String.format("Error: %s", e.getMessage());
                handler.post(() -> view.onRegisterError(errorMessage));
            }
        }).start();
    }
}
