package com.example.recuperarte.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import android.view.View;

import android.widget.TextView;

import com.example.recuperarte.R;

public class Login extends AppCompatActivity {

    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvSignUp = findViewById(R.id.textSignUP);
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
            finish();
        });
    }
}