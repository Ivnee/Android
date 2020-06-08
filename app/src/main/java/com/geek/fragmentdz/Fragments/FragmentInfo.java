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

import com.geek.fragmentdz.R;

import java.util.Objects;

public class FragmentInfo extends Fragment {
    private TextView cityName, temperature, date;
    private ImageView cityImage;
    private FrameLayout containImage;
    private int currentPosition = -1;

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
    }


    public void setData(String cityName, int temp, int currentPosition) {
        if (this.currentPosition != currentPosition) {
            this.currentPosition = currentPosition;
            this.cityName.setText(cityName);
            initTemperature(temp);
            createCoatOfArms(currentPosition);
        }
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
    }

    private void initViews(View view) {
        cityName = view.findViewById(R.id.cityname_text_view);
        date = view.findViewById(R.id.date_text_view);
        temperature = view.findViewById(R.id.temp_text_view);
        cityImage = view.findViewById(R.id.imageView);
    }

}
