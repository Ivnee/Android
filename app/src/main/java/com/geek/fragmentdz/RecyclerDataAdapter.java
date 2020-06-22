package com.geek.fragmentdz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.ViewHolder> {
    private ArrayList<String> data;
    private RVonClickListener clickListener;
    Context context;


    public RecyclerDataAdapter(ArrayList<String> data,RVonClickListener clickListener){
        this.data = data;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view =LayoutInflater.from(context).inflate(R.layout.rv_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = data.get(position);
        holder.rvItem.setText(text);
        setClickOnItem(holder,position);
    }
    public void add(String text){
        data.add(text);
        notifyItemInserted(data.size()-1);
    }
    public void clear(){
        data.clear();
        notifyDataSetChanged();
        add(context.getString(R.string.default_city));
    }

    private void setClickOnItem(ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null){
                    clickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data == null ? 0: data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView rvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvItem = itemView.findViewById(R.id.rv_item);
        }

    }
}
