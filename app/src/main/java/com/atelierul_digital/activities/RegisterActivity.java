package com.atelierul_digital.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.atelierul_digital.R;
import com.atelierul_digital.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.validator.routines.EmailValidator;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout email_layout;
    private TextInputLayout password_layout;
    private TextInputLayout firstName_layout;
    private TextInputLayout lastName_layout;
    private TextInputLayout city_layout;
    private TextInputLayout phoneNumber_layout;
    private EditText email_editText;
    private EditText password_editText;
    private EditText firstName_editText;
    private EditText lastName_editText;
    private EditText phoneNumber_editText;
    private AutoCompleteTextView city_autoCompleteText;
    private Button register_button;
    private TextView alreadyRegistered_text;
    private ProgressBar loading_progressBar;

    private FirebaseAuth mAuth;
    private DatabaseReference usersDatabase;

    public static boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public static boolean isValidfirstName(String firstName) {
        return firstName.matches("([A-Z][a-z]+)+([ -]([A-Z][a-z]+))*");
    }

    public static boolean isValidLastName(String lastName) {
        return lastName.matches("[A-Z][a-z]+");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^(\\+4|)?(07[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?" +
                "(\\s|\\.|\\-)?([0-9]{3}(\\s|\\.|\\-|)){2}$");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        usersDatabase = FirebaseDatabase.getInstance().getReference("users");

        email_layout = findViewById(R.id.email_layout);
        email_editText = findViewById(R.id.email_editText);
        password_layout = findViewById(R.id.password_layout);
        password_editText = findViewById(R.id.password_editText);
        firstName_layout = findViewById(R.id.firstName_layout);
        firstName_editText = findViewById(R.id.firstName_editText);
        lastName_layout = findViewById(R.id.lastName_layout);
        lastName_editText = findViewById(R.id.lastName_editText);
        city_layout = findViewById(R.id.city_layout);
        city_autoCompleteText = findViewById(R.id.city_autoCompleteText);
        phoneNumber_layout = findViewById(R.id.phoneNumber_layout);
        phoneNumber_editText = findViewById(R.id.phoneNumber_editText);
        register_button = findViewById(R.id.register_button);
        alreadyRegistered_text = findViewById(R.id.alreadyRegistered_text);
        loading_progressBar = findViewById(R.id.loading_progressBar);

        email_layout.setOnClickListener(this);
        email_editText.setOnClickListener(this);
        password_layout.setOnClickListener(this);
        password_editText.setOnClickListener(this);
        firstName_layout.setOnClickListener(this);
        firstName_editText.setOnClickListener(this);
        lastName_layout.setOnClickListener(this);
        lastName_editText.setOnClickListener(this);
        city_layout.setOnClickListener(this);
        city_autoCompleteText.setOnClickListener(this);
        phoneNumber_layout.setOnClickListener(this);
        phoneNumber_editText.setOnClickListener(this);
        register_button.setOnClickListener(this);
        alreadyRegistered_text.setOnClickListener(this);

        email_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email_editText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

        firstName_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstName_editText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lastName_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastName_editText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String[] cities = getResources().getStringArray(R.array.cityNames);
        ArrayAdapter<String>
                adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,
                cities);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        city_autoCompleteText.setAdapter(adapter);
        city_autoCompleteText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String enteredText = city_autoCompleteText.getText().toString();

                    ListAdapter listAdapter = city_autoCompleteText.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); ++i) {
                        String temp = listAdapter.getItem(i).toString();
                        if (enteredText.compareTo(temp) == 0) {
                            return;
                        }
                    }

                    city_autoCompleteText.setText("");
                }
            }
        });

        phoneNumber_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneNumber_editText.setError(null);
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
            case R.id.email_layout:
            case R.id.email_editText:
                email_editText.setError(null);
                break;
            case R.id.password_layout:
            case R.id.password_editText:
                password_editText.setError(null);
                password_layout.setPasswordVisibilityToggleEnabled(true);
                break;
            case R.id.firstName_layout:
            case R.id.firstName_editText:
                firstName_editText.setError(null);
                break;
            case R.id.lastName_layout:
            case R.id.lastName_editText:
                lastName_editText.setError(null);
                break;
            case R.id.city_layout:
            case R.id.city_autoCompleteText:
                ((AutoCompleteTextView) findViewById(R.id.city_autoCompleteText)).dismissDropDown();
                city_autoCompleteText.setError(null);
                break;
            case R.id.phoneNumber_layout:
            case R.id.phoneNumber_editText:
                phoneNumber_editText.setError(null);
                break;
            case R.id.register_button:
                loading_progressBar.setVisibility(View.VISIBLE);
                registerUser();
                break;
            case R.id.alreadyRegistered_text:
                startSignInActivity();
                break;
        }
    }

    private void registerUser() {
        String email = email_editText.getText().toString();
        String password = password_editText.getText().toString();
        String firstName = firstName_editText.getText().toString();
        String lastName = lastName_editText.getText().toString();
        String city = city_autoCompleteText.getText().toString();
        String phoneNumber = phoneNumber_editText.getText().toString();

        boolean isValidRegistration = true;

        if (!isValidEmail(email)) {
            email_editText.setError("Invalid email address");
            isValidRegistration = false;
        } else {
            email_editText.setError(null);
        }

        if (!isValidPassword(password).equals("")) {
            password_editText.setError(isValidPassword(password));
            password_layout.setPasswordVisibilityToggleEnabled(false);
            isValidRegistration = false;
        } else {
            password_editText.setError(null);
        }

        if (!isValidfirstName(firstName)) {
            firstName_editText.setError("Invalid First Name");
            isValidRegistration = false;
        } else {
            firstName_editText.setError(null);
        }

        if (!isValidLastName(lastName)) {
            lastName_editText.setError("Invalid Last Name");
            isValidRegistration = false;
        } else {
            lastName_editText.setError(null);
        }

        if (city.isEmpty()) {
            city_autoCompleteText.setError("Please select a city");
            isValidRegistration = false;
        } else {
            city_autoCompleteText.setError(null);
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            phoneNumber_editText.setError("Invalid Phone Number");
            isValidRegistration = false;
        } else {
            phoneNumber_editText.setError(null);
        }

        if (!isValidRegistration) {
            loading_progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful. " +
                                            "Please Sign In.", Toast.LENGTH_LONG).show();

                            String id = mAuth.getCurrentUser().getUid();
                            User user = new User(id, email, firstName, lastName, city, phoneNumber);
                            usersDatabase
                                    .child(id)
                                    .setValue(user).addOnCompleteListener(
                                    new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            mAuth.signOut();
                                            if (!task.isSuccessful()) {
                                                mAuth.getCurrentUser().delete();

                                                Toast.makeText(getApplicationContext(),
                                                        task.getException().getMessage(),
                                                        Toast.LENGTH_LONG)
                                                        .show();
                                            } else {
                                                startSignInActivity();
                                            }
                                        }
                                    });
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(),
                                        "You are already registered. Please Sign In.",
                                        Toast.LENGTH_LONG).show();
                                startSignInActivity();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        task.getException().getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                        loading_progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private String isValidPassword(String password) {
        if (!((password.length() >= 8)
                && (password.length() <= 15))) {
            return passwordErrorMessage(1);
        }
        if (password.contains(" ")) {
            return passwordErrorMessage(2);
        }
        if (true) {
            int count = 0;
            for (int i = 0; i <= 9; i++) {
                String str = Integer.toString(i);

                if (password.contains(str)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return passwordErrorMessage(3);
            }
        }
        if (!(password.contains("@") || password.contains("#") || password.contains("!") ||
                password.contains("~") || password.contains("$") || password.contains("%") ||
                password.contains("^") || password.contains("&") || password.contains("*") ||
                password.contains("(") || password.contains(")") || password.contains("-") ||
                password.contains("+") || password.contains("/") || password.contains(":") ||
                password.contains(".") || password.contains(",") || password.contains("<") ||
                password.contains(">") || password.contains("?") || password.contains("|"))) {
            return passwordErrorMessage(4);
        }
        if (true) {
            int count = 0;
            for (int i = 65; i <= 90; i++) {
                char c = (char) i;
                String str = Character.toString(c);

                if (password.contains(str)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return passwordErrorMessage(5);
            }
        }
        if (true) {
            int count = 0;
            for (int i = 90; i <= 122; i++) {
                char c = (char) i;
                String str = Character.toString(c);

                if (password.contains(str)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return passwordErrorMessage(6);
            }
        }

        return "";
    }

    private String passwordErrorMessage(int errorCode) {
        switch (errorCode) {
            case 1:
                return ("Should contain between 8 to 15 characters");
            case 2:
                return ("Should not contain any space");
            case 3:
                return ("Should contain at least 1 digit (0-9)");
            case 4:
                return ("Should contain at least 1 special character (@#!~$%^&*()-+/:.,<>?|)");
            case 5:
                return ("Should contain at least 1 uppercase letter (A-Z)");
            case 6:
                return ("Should contain at least 1 lowercase letter (a-z)");
        }
        return ("");
    }

    private void startSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}