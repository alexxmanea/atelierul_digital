package com.atelierul_digital;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Listener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frame, new MainFragment(), "main")
                .add(R.id.second_frame, new SecondFragment(), "second")
                .commit();
    }

    @Override
    public void analyze(String height) {
        SecondFragment secondFragment = (SecondFragment) getSupportFragmentManager().findFragmentByTag("second");
        secondFragment.setHeight(height);
    }

    @Override
    public void clearEditText() {
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("main");
        mainFragment.clearEditText();
    }
}