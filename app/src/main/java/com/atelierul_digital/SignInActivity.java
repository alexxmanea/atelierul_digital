package com.atelierul_digital;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private Button google_button;
    private Button facebook_button;
    private Button signIn_button;
    private LinearLayout signIn_details_linearLayout;
    private TextInputLayout email_layout;
    private TextInputLayout password_layout;
    private EditText email_editText;
    private EditText password_editText;
    private TextView forgotPassword_text;
    private TextView createAccount_text;
    private ProgressBar loading_progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        google_button = findViewById(R.id.google_button);
        facebook_button = findViewById(R.id.facebook_button);
        signIn_button = findViewById(R.id.signIn_button);
        signIn_details_linearLayout = findViewById(R.id.signIn_details_linearLayout);
        email_layout = findViewById(R.id.email_layout);
        email_editText = findViewById(R.id.email_editText);
        password_layout = findViewById(R.id.password_layout);
        password_editText = findViewById(R.id.password_editText);
        forgotPassword_text = findViewById(R.id.forgotPassword_text);
        createAccount_text = findViewById(R.id.createAccount_text);
        loading_progressBar = findViewById(R.id.loading_progressBar);

        google_button.setOnClickListener(this);
        facebook_button.setOnClickListener(this);
        signIn_button.setOnClickListener(this);
        signIn_details_linearLayout.setOnClickListener(this);
        email_layout.setOnClickListener(this);
        password_layout.setOnClickListener(this);
        email_editText.setOnClickListener(this);
        password_editText.setOnClickListener(this);
        forgotPassword_text.setOnClickListener(this);
        createAccount_text.setOnClickListener(this);

        password_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password_editText.setError(null);
                password_layout.setPasswordVisibilityToggleEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_button:
                break;
            case R.id.facebook_button:
                break;
            case R.id.signIn_button:
                loading_progressBar.setVisibility(View.VISIBLE);
                userSignIn();
                break;
            case R.id.signIn_details_linearLayout:
                break;
            case R.id.email_layout:
            case R.id.email_editText:
                email_editText.setError(null);
                break;
            case R.id.password_layout:
            case R.id.password_editText:
                password_editText.setError(null);
                password_layout.setPasswordVisibilityToggleEnabled(true);
                break;
            case R.id.forgotPassword_text:
                break;
            case R.id.createAccount_text:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    private void userSignIn() {
        String email = email_editText.getText().toString();
        String password = password_editText.getText().toString();

        boolean isValidRegistration = true;

        if (!RegisterActivity.isValidEmail(email)) {
            email_editText.setError("Invalid email address");

            if (email.isEmpty()) {
                email_editText.setError("Please enter your email address");
            }

            isValidRegistration = false;
        } else {
            email_editText.setError(null);
        }

        if (password.isEmpty()) {
            password_editText.setError("Please enter your password");
            password_layout.setPasswordVisibilityToggleEnabled(false);
            isValidRegistration = false;
        } else {
            password_editText.setError(null);
        }

        if (!isValidRegistration) {
            loading_progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Signed In successful",
                                    Toast.LENGTH_SHORT).show();

                            startMainActivity();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    task.getException().getMessage(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
        loading_progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            startMainActivity();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}