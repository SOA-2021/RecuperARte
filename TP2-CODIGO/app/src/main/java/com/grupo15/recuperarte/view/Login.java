package com.grupo15.recuperarte.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.content.Intent;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.grupo15.recuperarte.R;
import com.grupo15.recuperarte.mvp.ILogin;
import com.grupo15.recuperarte.network.NetworkChecker;
import com.grupo15.recuperarte.presenter.LoginPresenter;

public class Login extends AppCompatActivity implements ILogin.View {
    private ILogin.Presenter presenter;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        /* Link hacia el registro */
        TextView tvSignUp = findViewById(R.id.textSignUP);
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
            finish();
        });

        /* Boton de login */
        loginButton = findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(b -> doLogin());

        final ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkChecker nc = new NetworkChecker(cm);
        this.presenter = new LoginPresenter(this, nc);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if ( result == null ) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        this.presenter.readQR(result);
    }

    private void doLogin() {
        loginButton.setActivated(false);

        EditText emailField = findViewById(R.id.emailField);
        EditText passwordField = findViewById(R.id.passwordField);

        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        this.presenter.login(email, password);
    }

    @Override
    public void initiateScan() {
        IntentIntegrator integrator = new IntentIntegrator(Login.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("LECTOR DE CODIGO QR");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    public void onLoginError(String errorMessage) {
        loginButton.setActivated(true);
        Toast.makeText(
                getBaseContext(),
                errorMessage,
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(
                getBaseContext(),
                "Login correcto",
                Toast.LENGTH_SHORT
        ).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}