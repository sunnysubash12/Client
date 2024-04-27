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

public class repAdapter extends RecyclerView.Adapter<repAdapter.ViewHolder> {
    List<repList> list;
    Context context;

    public repAdapter(List<repList> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reports, viewGroup, false);

        return new repAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull repAdapter.ViewHolder viewHolder, int i) {
        repList myList = list.get(i);
        viewHolder.date.setText(myList.getDates());
        viewHolder.name.setText(myList.getNames());
        viewHolder.doctor.setText(myList.getDoctors());
        viewHolder.description.setText(myList.getDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, name,doctor,description;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dates);
            name = itemView.findViewById(R.id.name);
            doctor = itemView.findViewById(R.id.pDoctor);
            description = itemView.findViewById(R.id.description);
            cardView = itemView.findViewById(R.id.cd2);
        }
    }
}
