package com.grupo15.recuperarte.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import com.grupo15.recuperarte.R;
import com.grupo15.recuperarte.api_catedra.api.ApiException;
import com.grupo15.recuperarte.api_catedra.api.IApiClient;
import com.grupo15.recuperarte.api_catedra.model.User;
import com.grupo15.recuperarte.global.Conf;
import com.grupo15.recuperarte.mvp.ISignUp;
import com.grupo15.recuperarte.presenter.SignUpPresenter;

public class SignUp extends AppCompatActivity implements ISignUp.View {
    private Button signUpButton;
    private ISignUp.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        /* El link hacia el login desde la pantalla de registro */
        TextView tvLogin = findViewById(R.id.textLogin);
        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
            finish();
        });

        /* Boton de registro. Si lo aprieta, mando a registrar */
        signUpButton = findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(b -> doSignUp());

        this.presenter = new SignUpPresenter(this);
    }

    private void doSignUp() {
        signUpButton.setActivated(false);

        EditText nameInput = findViewById(R.id.name_input);
        EditText lastnameInput = findViewById(R.id.lastname_input);
        EditText dniInput = findViewById(R.id.dni_input);
        EditText emailInput = findViewById(R.id.email_input);
        EditText passwordInput = findViewById(R.id.password_input);

        String name = nameInput.getText().toString();
        String lastname = lastnameInput.getText().toString();
        String dni = dniInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        this.presenter.registerUser(name, lastname, dni, email, password);
    }

    @Override
    public void registerOnSuccess() {
        Toast.makeText(
                getBaseContext(),
                "Registro correcto",
                Toast.LENGTH_SHORT
        ).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void registerOnError(String errorMessage) {
        signUpButton.setActivated(true);
        Toast.makeText(
                getBaseContext(),
                errorMessage,
                Toast.LENGTH_SHORT
        ).show();
    }
}