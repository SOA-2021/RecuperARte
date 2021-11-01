package com.grupo15.recuperarte.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.grupo15.recuperarte.R;
import com.grupo15.recuperarte.api_catedra.api.IApiClient;
import com.grupo15.recuperarte.api_catedra.model.User;
import com.grupo15.recuperarte.api_catedra.request.RegisterRequest;
import com.grupo15.recuperarte.global.Conf;

public class SignUp extends AppCompatActivity {

    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        TextView tvLogin = findViewById(R.id.textLogin);
        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
            finish();
        });

        EditText nameField = findViewById(R.id.nameField);
        EditText lastnameField = findViewById(R.id.lastnameField);
        EditText dniField = findViewById(R.id.dniField);
        EditText emailField = findViewById(R.id.emailField);
        EditText passwordField = findViewById(R.id.passwordField);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v-> {
            User user = new User(nameField.getText().toString(),lastnameField.getText().toString(),
                    Integer.parseInt(dniField.getText().toString()),emailField.getText().toString());
            String password = passwordField.getText().toString();

                }

        );
    }
}