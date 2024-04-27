package com.example.dissertation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class medAdapter extends RecyclerView.Adapter<medAdapter.ViewHolder> {
    List<medicineList> list;
    Context context;

    public medAdapter(List<medicineList> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medicines, viewGroup, false);

        return new medAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull medAdapter.ViewHolder viewHolder, int i) {
        medicineList myList = list.get(i);
        viewHolder.medicineName.setText(myList.getMedNam());
        viewHolder.dosage.setText(myList.getDosage());
        viewHolder.frequency.setText(myList.getFreq());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView medicineName, dosage, frequency;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.medicineName);
            dosage = itemView.findViewById(R.id.dosage);
            frequency = itemView.findViewById(R.id.frequency);
            cardView = itemView.findViewById(R.id.cd2);
        }
    }
}
