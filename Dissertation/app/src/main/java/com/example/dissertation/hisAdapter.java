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

public class hisAdapter extends RecyclerView.Adapter<hisAdapter.ViewHolder> {
    List<hisList> list;
    Context context;

    public hisAdapter(List<hisList> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history, viewGroup, false);

        return new hisAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull hisAdapter.ViewHolder viewHolder, int i) {
        hisList myList = list.get(i);
        viewHolder.report1.setText(myList.getDate1());
        viewHolder.report2.setText(myList.getDate2());
        viewHolder.doctor1.setText(myList.getDoctors1());
        viewHolder.doctor2.setText(myList.getDoctors2());
        viewHolder.description1.setText(myList.getDescription1());
        viewHolder.description2.setText(myList.getDescription2());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView report1,report2, doctor1,doctor2,description1,description2;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            report1 = itemView.findViewById(R.id.date1);
            report2 = itemView.findViewById(R.id.date2);
            doctor1 = itemView.findViewById(R.id.doctor1);
            doctor2 = itemView.findViewById(R.id.doctor2);
            description1 = itemView.findViewById(R.id.description1);
            description2 = itemView.findViewById(R.id.description2);
        }
    }
}
