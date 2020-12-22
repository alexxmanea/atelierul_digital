package com.atelierul_digital;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Listener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.second_frame, new SecondFragment(), "tag")
                .commit();
    }

    @Override
    public void analyze(String height) {
        SecondFragment secondFragment = (SecondFragment) getSupportFragmentManager().findFragmentByTag("tag");
        secondFragment.setHeight(height);
    }
}