package com.geek.fragmentdz.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.geek.fragmentdz.RVWeatherContainer;
import com.geek.fragmentdz.RVonClickListener;
import com.geek.fragmentdz.RecyclerDataAdapter;
import com.geek.fragmentdz.RecyclerDateDataAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class FragmentInfo extends Fragment implements RVonClickListener {
    private TextView cityName, temperature, date;
    private FrameLayout containImage;
    private int currentPosition = -1;
    private RecyclerView history;
    private Button info;


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
        setInfoBtnListener(view);
    }

    private void setInfoBtnListener(View view) {
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri url = Uri.parse("https://yandex.ru/pogoda/");
                Intent intent = new Intent(Intent.ACTION_VIEW,url);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public void setData(String cityName, int temp, int currentPosition) {
        if (this.currentPosition != currentPosition) {
            this.currentPosition = currentPosition;
            this.cityName.setText(cityName);
            initTemperature(temp);
            createWeatherImg(currentPosition);
            initWeatherRV();
            initDate();
        }
    }



    private void initWeatherRV() {
        ArrayList<RVWeatherContainer> arr = new ArrayList<RVWeatherContainer>(Arrays.asList
                (new RVWeatherContainer("15:00", Objects.requireNonNull(getActivity()).getDrawable(R.drawable.icon1),"+24"),
                        new RVWeatherContainer("16:00", Objects.requireNonNull(getActivity()).getDrawable(R.drawable.icon1),"+22"),
                        new RVWeatherContainer("17:00", Objects.requireNonNull(getActivity()).getDrawable(R.drawable.icon2),"+21"),
                        new RVWeatherContainer("18:00", Objects.requireNonNull(getActivity()).getDrawable(R.drawable.icon2),"+19"),
                        new RVWeatherContainer("19:00", Objects.requireNonNull(getActivity()).getDrawable(R.drawable.icon3),"+18"),
                        new RVWeatherContainer("20:00", Objects.requireNonNull(getActivity()).getDrawable(R.drawable.icon3),"+16"),
                        new RVWeatherContainer("21:00", Objects.requireNonNull(getActivity()).getDrawable(R.drawable.icon4),"+15")));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerDateDataAdapter adapter = new RecyclerDateDataAdapter(arr);
        history.setLayoutManager(layoutManager);
        history.setAdapter(adapter);
    }

    private void initDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateText = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        date.setText(dateText);
    }

    private void initTemperature(int temp) {
        int tempColor;
        if (temp > 0) {
            String t = "+" + String.valueOf(temp);
            temperature.setText(t);
            tempColor = ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.yellow);
            temperature.setTextColor(tempColor);
        } else {
            String t = String.valueOf(temp);
            temperature.setText(t);
            tempColor = ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorPrimary);
            temperature.setTextColor(tempColor);
        }
    }

    private void createWeatherImg(int currentPosition) {
        TypedArray images = getResources().obtainTypedArray(R.array.weather_img_arr);
        ImageView coatOfArms = new ImageView(getActivity());
        containImage.removeAllViews();
        coatOfArms.setImageResource(images.getResourceId(currentPosition % images.length() , -1));
        containImage.addView(coatOfArms);
    }

    private void initViews(View view) {
        info = view.findViewById(R.id.info_btn);
        history = view.findViewById(R.id.history_list);
        cityName = view.findViewById(R.id.cityname_text_view);
        date = view.findViewById(R.id.date_text_view);
        temperature = view.findViewById(R.id.temp_text_view);
        containImage = view.findViewById(R.id.contain_image);
    }

    @Override
    public void onItemClick(int position) {
        System.out.println(position);
    }
}
