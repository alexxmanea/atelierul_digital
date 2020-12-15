package com.atelierul_digital;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewActivity extends AppCompatActivity {

    private String height;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);

        if (getIntent().getExtras() != null) {
            height = getIntent().getStringExtra("height");
        }


        Button analyze_button = findViewById(R.id.analyze_button);
        TextView analyze_height_textview = findViewById(R.id.analyze_height_textview);

        analyze_height_textview.setText(height);

        analyze_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int heightInCm = Integer.valueOf(height);

                    String isEnough = (heightInCm > 185) ? "YES" : "NO";


                    Log.i("Lifecycle", "se apeleaza" + isEnough);

                    Intent intent = new Intent();
                    intent.putExtra("height_result", isEnough);
                    setResult(Activity.RESULT_OK, intent);

                    finish();
                } catch (NumberFormatException e) {
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
            }
        });
        Log.i("Lifecycle", "onCreate -> " + height);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Lifecycle", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Lifecycle", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Lifecycle", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Lifecycle", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Lifecycle", "onRestart");
    }
}
