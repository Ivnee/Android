package com.geek.trener.Fragments;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.geek.trener.InfoContainer;
import com.geek.trener.R;

import java.util.Date;
import java.util.Objects;

public class FragmentInfo extends Fragment {
    private ImageView cityImage;
    private TextView temperature, date,cityName;

    static FragmentInfo setInfo(InfoContainer container) {
        FragmentInfo fragment = new FragmentInfo();

        Bundle info = new Bundle();
        info.putSerializable("info", container);
        fragment.setArguments(info);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        TypedArray images = getResources().obtainTypedArray(R.array.array_city_imgs);
        cityImage.setImageResource(images.getResourceId(getIndex(),-1));
        cityName.setText(getCityName());
        initDate();
        initTemp();
    }

    private void initTemp() {
        int tempColor;
        if(getTemp()>0){
            String t = "+"+String.valueOf(getTemp());
            temperature.setText(t);
            tempColor = ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.yellow);
            temperature.setTextColor(tempColor);
        }else{
            String t = String.valueOf(getTemp());
            temperature.setText(t);
            tempColor = ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorPrimary);
            temperature.setTextColor(tempColor);
        }

    }

    private void initDate() {
        SimpleDateFormat sdf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdf = new SimpleDateFormat("dd,MM,yyyy");
            String textDate = sdf.format(new Date(System.currentTimeMillis()));
            date.setText(textDate);
        }
    }

    private InfoContainer getInfo(){
        return (InfoContainer) Objects.requireNonNull(getArguments()).getSerializable("info");

    }

    int getIndex(){
        try {
            return getInfo().currentPosition;
        }catch (Exception e){
            return 0;
        }
    }

    private String getCityName(){
        try{
            return getInfo().cityName;
        }catch (Exception e){
            return "";
        }
    }
    private int getTemp(){
        try {
            return getInfo().temperature;
        }catch (Exception e){
            return 0;
        }
    }

    private void initViews(View view) {
        cityImage = view.findViewById(R.id.city_image);
        temperature = view.findViewById(R.id.temperature);
        date = view.findViewById(R.id.date_view);
        cityName = view.findViewById(R.id.city_name_text_view);
    }

}
