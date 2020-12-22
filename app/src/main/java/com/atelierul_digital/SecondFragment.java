package com.atelierul_digital;

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
    private Button analyze_button;
    private TextView analyze_height_textview;
    private String height;

    public void setHeight(String height) {
        this.height = height;
        analyze_height_textview.setText(height + " cm");
        evaluateAnalyzeState();
    }

    private void evaluateAnalyzeState() {
        analyze_button.setEnabled(height != null);
        analyze_height_textview.setVisibility((height != null) ? View.VISIBLE : View.INVISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.second_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        analyze_button = view.findViewById(R.id.analyze_button);
        analyze_height_textview = view.findViewById(R.id.analyze_height_textview);

        setHeight(null);

        analyze_button.setOnClickListener(v -> {
            try {
                int heightInCm = Integer.valueOf(height);

                if (heightInCm < 0) {
                    Toast.makeText(getActivity(), "Invalid height", Toast.LENGTH_SHORT).show();
                }

                String isEnough = (heightInCm >= 185) ? "YES" : "NO";

                Toast.makeText(getActivity(), "Was my height enough? - " + isEnough, Toast.LENGTH_SHORT).show();
                setHeight(null);
            } catch (NumberFormatException e) {
                Toast.makeText(getActivity(), "Invalid height", Toast.LENGTH_SHORT).show();
            } finally {
                setHeight(null);
            }
        });
    }
}
