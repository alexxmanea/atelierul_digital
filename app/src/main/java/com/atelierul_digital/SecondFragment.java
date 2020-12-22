package com.atelierul_digital;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {
    private String height = null;
    private Button analyze_button;
    TextView analyze_height_textview;

    public void setHeight(String height) {
        this.height = height;
        evaluateAnalyzeState();
        analyze_height_textview.setText(height + " cm");
    }

    private void evaluateAnalyzeState() {
        analyze_button.setEnabled(height != null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        analyze_button = view.findViewById(R.id.analyze_button);
        evaluateAnalyzeState();

        analyze_height_textview = view.findViewById(R.id.analyze_height_textview);

        analyze_button.setOnClickListener(v -> {
            try {
                int heightInCm = Integer.valueOf(height);

                if (heightInCm < 0) {
                    Toast.makeText(getActivity(), "Invalid height", Toast.LENGTH_SHORT).show();
                }

                String isEnough = (heightInCm >= 185) ? "YES" : "NO";

                Intent intent = new Intent();
                intent.putExtra("height_result", isEnough);
                Toast.makeText(getActivity(), "Was my height enough? - " + isEnough, Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(getActivity(), "Invalid height", Toast.LENGTH_SHORT).show();
            } finally {
                height = null;
                evaluateAnalyzeState();
            }
        });
    }
}
