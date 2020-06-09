package com.geek.fragmentdz.Fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.fragmentdz.R;
import com.geek.fragmentdz.RVonClickListener;
import com.geek.fragmentdz.RecyclerDataAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class FragmentInfo extends Fragment implements RVonClickListener {
    private TextView cityName, temperature, date;
    private ImageView cityImage;
    private FrameLayout containImage;
    private int currentPosition = -1;
    private RecyclerView history;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        containImage = view.findViewById(R.id.contain_image);
        initViews(view);

//что я пытался сделать для сохранения данных при перевороте экрана
/*        if(savedInstanceState != null){
            currentPosition = savedInstanceState.getInt("index");
            cityName.setText(savedInstanceState.getString("cityName"));
            temperature.setText(savedInstanceState.getString("temp"));
            createCoatOfArms(currentPosition);
        }*/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
//что я пытался сделать для сохранения данных при перевороте экрана
/*    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("index",currentPosition);
        outState.putString("cityName", (String) cityName.getText());
        outState.putString("temp", (String) temperature.getText());
        super.onSaveInstanceState(outState);
    }*/

    public void setData(String cityName, int temp, int currentPosition) {
        if (this.currentPosition != currentPosition) {
            this.currentPosition = currentPosition;
            this.cityName.setText(cityName);
            initTemperature(temp);
            createCoatOfArms(currentPosition);
        }
    }

    private  void initDate(){
        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("dd.MM.yyyy");
        String dateText = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        date.setText(dateText);
    }
    private void initTemperature(int temp) {
        int tempColor;
        if(temp>0){
            String t = "+"+String.valueOf(temp);
            temperature.setText(t);
            tempColor = ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.yellow);
            temperature.setTextColor(tempColor);
        }else{
            String t = String.valueOf(temp);
            temperature.setText(t);
            tempColor = ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorPrimary);
            temperature.setTextColor(tempColor);
        }
    }

    private void createCoatOfArms(int currentPosition) {
        TypedArray images = getResources().obtainTypedArray(R.array.array_city_imgs);
        ImageView coatOfArms = new ImageView(getActivity());
        containImage.removeAllViews();
        coatOfArms.setImageResource(images.getResourceId(currentPosition, -1));
        containImage.addView(coatOfArms);
        initDate();
        ArrayList<String > arr= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.default_history)));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerDataAdapter adapter = new RecyclerDataAdapter(arr,this);
        history.setLayoutManager(layoutManager);
        history.setAdapter(adapter);
    }

    private void initViews(View view) {
        history = view.findViewById(R.id.history_list);
        cityName = view.findViewById(R.id.cityname_text_view);
        date = view.findViewById(R.id.date_text_view);
        temperature = view.findViewById(R.id.temp_text_view);
        cityImage = view.findViewById(R.id.imageView);
    }

    @Override
    public void onItemClick(int position) {
        System.out.println(position);
    }
}
