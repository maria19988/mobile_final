package com.example.user.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.myapplication.models.Job;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<Job> android;

    public DataAdapter(ArrayList<Job> android) {
        this.android = android;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activity, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.title_holder.setText(android.get(i).getTitle());
        viewHolder.description_holder.setText(android.get(i).getDescription());
        viewHolder.phone_holder.setText(android.get(i).getPhone());
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title_holder,description_holder,phone_holder;
        public ViewHolder(View view) {
            super(view);

            title_holder = (TextView)view.findViewById(R.id.title_holder);
            description_holder = (TextView)view.findViewById(R.id.description_holder);
            phone_holder = (TextView)view.findViewById(R.id.phone_holder);

        }
    }

}