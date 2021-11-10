package com.grupo15.recuperarte.presenter;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.grupo15.recuperarte.api_catedra.api.ApiException;
import com.grupo15.recuperarte.api_catedra.api.IApiClient;
import com.grupo15.recuperarte.api_catedra.model.User;
import com.grupo15.recuperarte.global.Conf;
import com.grupo15.recuperarte.mvp.ISignUp;

public class SignUpPresenter implements ISignUp.Presenter {
    private Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
    private ISignUp.View view;

    public SignUpPresenter(ISignUp.View view) { this.view = view;}

    @Override
    public void registerUser(String name, String lastname, String dni, String email, String psw) {
        new Thread(()-> {
            final Integer dniAsInt;
            try {
                dniAsInt = Integer.parseInt(dni);
            } catch ( NumberFormatException e ) {
                String errorMessage = String.format("El dni debe ser numerico");
                handler.post(()->view.registerOnError(errorMessage));
                return;
            }

            Conf c = Conf.getInstance();
            IApiClient api = c.apiClient();
            User newUser = new User(name, lastname, dniAsInt, email);
            try {
                api.registerUser(newUser, psw);
                handler.post(view::registerOnSuccess);
            } catch ( ApiException e ) {
                String errorMessage = String.format("Error: %s", e.getMessage());
                handler.post(() -> view.registerOnError(errorMessage));
            }
        }).start();
    }
}
