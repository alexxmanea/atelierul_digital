package com.atelierul_digital;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecycleViewHolder> {
    private final List<Name> data;

    public RecyclerViewAdapter(List<Name> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View recyclerRow = layoutInflater.inflate(R.layout.recycler_item, viewGroup, false);
        return new RecycleViewHolder(recyclerRow);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder recycleViewHolder, int position) {
        String firstName = data.get(position).getFirstName();
        String lastName = data.get(position).getLastName();

        recycleViewHolder.firstName_textView.setText(firstName);
        recycleViewHolder.lastName_textView.setText(lastName);

        if(Integer.parseInt(firstName.substring(firstName.length() - 1)) % 2 == 0) {
            recycleViewHolder.linearLayout.setBackgroundColor(Color.parseColor("#fafafa"));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder {
        final TextView firstName_textView;
        final TextView lastName_textView;
        final LinearLayout linearLayout;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.firstName_textView = itemView.findViewById(R.id.firstName);
            this.lastName_textView = itemView.findViewById(R.id.lastName);
            this.linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}