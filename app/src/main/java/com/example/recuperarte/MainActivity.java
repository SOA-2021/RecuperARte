package com.example.recuperarte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.andrognito.patternlockview.PatternLockView;

public class MainActivity extends AppCompatActivity {

    String savePatternKey = "patternCode";
    String finalPattern = "";
    PatternLockView mPatternLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}