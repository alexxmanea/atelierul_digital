package com.atelierul_digital;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {
    private EditText height_editText;
    private Button navigate_button;

    public void clearEditText() {
        height_editText.setText("");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        height_editText = view.findViewById(R.id.edittext_height);
        navigate_button = view.findViewById(R.id.button_navigate);

        navigate_button.setOnClickListener(v -> {
            String height = height_editText.getText().toString();
            ((Listener) getActivity()).analyze(height);
        });
    }
}
