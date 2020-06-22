package com.geek.fragmentdz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleTimeWeatherAdapter extends RecyclerView.Adapter<RecycleTimeWeatherAdapter.ViewHolder> {
    ArrayList<RVWeatherContainer> data ;

    public RecycleTimeWeatherAdapter(ArrayList<RVWeatherContainer> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_weather,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.time.setText(data.get(position).time);
        holder.img.setImageDrawable(data.get(position).image);
        holder.temp.setText(data.get(position).temp);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView time ;
        ImageView img ;
        TextView temp ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.rv_weather_time);
            img = itemView.findViewById(R.id.rv_weather_img);
            temp = itemView.findViewById(R.id.rv_weather_temp);
        }
    }
}
