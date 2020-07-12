package com.geek.fragmentdz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerHistoryAdapter extends RecyclerView.Adapter<RecyclerHistoryAdapter.ViewHolder> {

    private ArrayList<History> data;

    public RecyclerHistoryAdapter(ArrayList<History> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String text;
        if(Integer.parseInt(data.get(position).temperature)> 0) {
            text = data.get(position).date + ": " + data.get(position).cityName + " +" + data.get(position).temperature;
        }else{
            text = data.get(position).date + ": " + data.get(position).cityName + " " + data.get(position).temperature;
        }
        holder.historyTextItem.setText(text);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView historyTextItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyTextItem = itemView.findViewById(R.id.rv_history_item);
        }

    }
}
