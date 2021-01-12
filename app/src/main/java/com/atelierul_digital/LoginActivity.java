package com.atelierul_digital;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String MY_SHARED_PREFS = "MY_PREFS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty()) {
                    return;
                }
                if (password.getText().toString().isEmpty()) {
                    return;
                }

                // TODO - validate login is correct
                saveUserSession();
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE);
        boolean isUserLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);

        if (isUserLoggedIn) {
            startMainActivity();
        }
    }

    private void saveUserSession() {
        SharedPreferences sharedPreferences = getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("is_logged_in", true).apply();
    }
}