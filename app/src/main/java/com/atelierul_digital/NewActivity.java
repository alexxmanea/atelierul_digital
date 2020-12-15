package com.atelierul_digital;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);

        Log.i("Licecycle", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Licecycle", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Licecycle", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Licecycle", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Licecycle", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Licecycle", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Licecycle", "onRestart");
    }
}
