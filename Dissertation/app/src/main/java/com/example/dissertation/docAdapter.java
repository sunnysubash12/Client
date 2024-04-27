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

public class docAdapter extends RecyclerView.Adapter<docAdapter.ViewHolder> {
    List<docterList> list;
    Context context;

    public docAdapter(List<docterList> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doctors, viewGroup, false);

        return new docAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull docAdapter.ViewHolder viewHolder, int i) {
        docterList myList = list.get(i);
        viewHolder.docName.setText(myList.getDocNam());
        viewHolder.specialty.setText(myList.getSpecial());
        viewHolder.availability.setText(myList.getAvail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView docName, specialty, availability;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            docName = itemView.findViewById(R.id.docName);
            specialty = itemView.findViewById(R.id.specialty);
            availability = itemView.findViewById(R.id.availability);
            cardView = itemView.findViewById(R.id.cd2);
        }
    }
}
