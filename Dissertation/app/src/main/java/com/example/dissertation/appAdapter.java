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

public class appAdapter extends RecyclerView.Adapter<appAdapter.ViewHolder> {
    List<appList> list;
    Context context;

    public appAdapter(List<appList> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.appointments, viewGroup, false);

        return new appAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull appAdapter.ViewHolder viewHolder, int i) {
        appList myList = list.get(i);
        viewHolder.date.setText(myList.getDate());
        viewHolder.time.setText(myList.getTime());
        viewHolder.doctor.setText(myList.getDoctor());
        viewHolder.purpose.setText(myList.getPurpose());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, time,doctor,purpose;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            doctor = itemView.findViewById(R.id.doctor);
            purpose = itemView.findViewById(R.id.purpose);
            cardView = itemView.findViewById(R.id.cd2);
        }
    }
}
