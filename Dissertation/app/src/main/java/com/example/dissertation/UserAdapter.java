package com.example.dissertation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    List<UserList> list;
    Context context;

    public UserAdapter(List<UserList> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercises, viewGroup, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder viewHolder, int i) {
        UserList myList = list.get(i);
        viewHolder.exercise.setText(myList.getName());
        viewHolder.set.setText(String.valueOf(myList.getSets())); // Convert integer to String
        viewHolder.timme.setText(myList.getTimme());
        // Load icon image using Glide or any other image loading library
        Glide.with(viewHolder.itemView.getContext())
                .load(myList.getPic())
                .into(viewHolder.picUrl);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView exercise, set, timme;
        ImageView picUrl;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picUrl = itemView.findViewById(R.id.exerImg);
            exercise = itemView.findViewById(R.id.ecerName);
            set = itemView.findViewById(R.id.sets);
            timme = itemView.findViewById(R.id.timme);
            cardView = itemView.findViewById(R.id.cd);
        }
    }
}
