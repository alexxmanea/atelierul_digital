package com.atelierul_digital;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout layout_1 = findViewById(R.id.layout_1);
        FrameLayout layout_2 = findViewById(R.id.layout_2);
        FrameLayout layout_3 = findViewById(R.id.layout_3);

        final int[] width = new int[1];
        final int[] height = new int[1];

        ViewTreeObserver vto = layout_1.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout_1.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                width[0] = layout_1.getMeasuredWidth();
                height[0] = layout_1.getMeasuredHeight();
            }
        });

        layout_2.post(new Runnable() {
            @Override
            public void run() {
                int width2 = width[0] - (height[0] / 2 - height[0] / 3);
                int height2 = height[0] / 2;

                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width2, height2);
                layoutParams.gravity = Gravity.CENTER;
                layout_2.setLayoutParams(layoutParams);
            }
        });

        layout_3.post(new Runnable() {
            @Override
            public void run() {
                int width3 = width[0] - 2 * (height[0] / 2 - height[0] / 3);
                int height3 = height[0] / 3;

                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width3, height3);
                layoutParams.gravity = Gravity.CENTER;
                layout_3.setLayoutParams(layoutParams);
            }
        });
    }
}