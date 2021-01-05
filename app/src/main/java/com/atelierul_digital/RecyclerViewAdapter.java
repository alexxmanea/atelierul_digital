package com.atelierul_digital;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecycleViewHolder> {
    private List<Person> data = new ArrayList<>();

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View recyclerRow = layoutInflater.inflate(R.layout.recycler_item, viewGroup, false);
        return new RecycleViewHolder(recyclerRow);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder recycleViewHolder, int position) {
        StringBuilder allData = new StringBuilder();
        allData.append(data.get(position).getName());
        allData.append(" ");
        allData.append(data.get(position).getSurname());
        allData.append("\n");
        allData.append(data.get(position).getAddress());

        recycleViewHolder.dataTextView.setText(allData.toString());

        if (position % 2 == 0) {
            recycleViewHolder.linearLayout.setBackgroundColor(Color.parseColor("#fafafa"));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Person> personList) {
        data.clear();
        data.addAll(personList);

        notifyDataSetChanged();
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder {
        final TextView dataTextView;
        final LinearLayout linearLayout;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.dataTextView = itemView.findViewById(R.id.dataTextView);
            this.linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}