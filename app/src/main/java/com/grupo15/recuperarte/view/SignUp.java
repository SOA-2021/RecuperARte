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

public class SignUp extends AppCompatActivity {
    Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

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
        Button signup = findViewById(R.id.signup_button);
        signup.setOnClickListener(b -> doSignUp((Button)b));
    }

    private void doSignUp(Button b) {
        b.setActivated(false);

        EditText nameInput = findViewById(R.id.name_input);
        EditText lastnameInput = findViewById(R.id.lastname_input);
        EditText emailInput = findViewById(R.id.email_input);
        EditText passwordInput = findViewById(R.id.password_input);

        String name = nameInput.getText().toString();
        String lastname = lastnameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        new Thread(()-> {
            Conf c = Conf.getInstance();
            IApiClient api = c.apiClient();
            User newUser = new User(name, lastname, 123, email);
            try {
                api.registerUser(newUser, password);
                handler.post(this::onSuccess);
            } catch ( ApiException e ) {
                handler.post(() -> onError(b, String.format("Error: %s", e.getMessage())));
            }
        }).start();
    }

    private void onSuccess() {
        Toast.makeText(
                getBaseContext(),
                "Registro correcto",
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