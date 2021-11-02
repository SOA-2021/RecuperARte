package com.grupo15.recuperarte.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import android.os.Bundle;

import android.content.Intent;

import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.grupo15.recuperarte.R;
import com.grupo15.recuperarte.api_catedra.api.ApiException;
import com.grupo15.recuperarte.api_catedra.api.IApiClient;
import com.grupo15.recuperarte.api_catedra.model.User;
import com.grupo15.recuperarte.global.Conf;

public class Login extends AppCompatActivity {

    Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        TextView tvSignUp = findViewById(R.id.textSignUP);
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
            finish();
        });
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(b -> doLogin((Button)b));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_LONG).show();
            } else {
                if (result.getContents().equals("123456789"))
                    handler.post(this::onSuccess);
                else
                    Toast.makeText(this, "Codigo Incorrecto", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void doLogin(Button b) {
        b.setActivated(false);

        EditText emailField = findViewById(R.id.emailField);
        EditText passwordField = findViewById(R.id.passwordField);

        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        new Thread(()-> {
            Conf c = Conf.getInstance();
            IApiClient api = c.apiClient();
            try {
                //api.login(email, password);
                User newUser = new User("leo", "san martin", 123, email);
                api.registerUser(newUser, password);
                IntentIntegrator integrator = new IntentIntegrator(Login.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("LECTOR DE CODIGO QR");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            } catch ( ApiException e ) {
                handler.post(() -> onError(b, String.format("Error: %s", e.getMessage())));
            }
        }).start();
    }

    private void onSuccess() {
        Toast.makeText(
                getBaseContext(),
                "Login correcto",
                Toast.LENGTH_SHORT
        ).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void onError(Button b, String errMsg) {
        b.setActivated(true);
        Toast.makeText(
                getBaseContext(),
                errMsg,
                Toast.LENGTH_SHORT
        ).show();
    }
}