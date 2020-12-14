package com.atelierul_digital;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    // from Stack Overflow
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidPhone(CharSequence target) {
        return target.toString().matches("^(\\+4|)?(07[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|\\-)?([0-9]{3}(\\s|\\.|\\-|)){2}$");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText email_editText = findViewById(R.id.email_edittext);
        EditText phone_editText = findViewById(R.id.phone_edittext);
        TextView details_editText = findViewById(R.id.details_edittext);
        CheckBox tc_checkBox = findViewById(R.id.tc_checkbox);
        Button submitButton = findViewById(R.id.submit_button);

        details_editText.setVisibility(View.INVISIBLE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidEmail(email_editText.getText().toString())) {
                    email_editText.setError("Fill the input with a valid email address");
                    return;
                } else if (!isValidPhone(phone_editText.getText().toString())) {
                    phone_editText.setError("Fill the input with a valid Romania phone number");
                    return;
                } else if (!tc_checkBox.isChecked()) {
                    tc_checkBox.setError("You must accept the T&C");
                    return;
                } else {
                    email_editText.setError(null);
                    phone_editText.setError(null);
                    Toast.makeText(getApplicationContext(), "You logged in successfully!", Toast.LENGTH_SHORT).show();

                    StringBuilder details = new StringBuilder();
                    details.append("Filled information:\n");
                    details.append("\nEmail: ");
                    details.append(email_editText.getText().toString());
                    details.append("\nPhone: ");
                    details.append(phone_editText.getText().toString());
                    details.append("\n\nT&C accepted.");

                    details_editText.setText(details.toString());
                    details_editText.setVisibility(View.VISIBLE);

                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                    }

                    Timer t = new Timer(false);
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    details_editText.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                    }, 5000);
                }
            }
        });

        tc_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tc_checkBox.setError(null);
            }
        });
    }
}