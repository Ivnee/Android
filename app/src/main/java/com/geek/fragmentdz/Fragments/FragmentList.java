package com.geek.fragmentdz.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.fragmentdz.Bus.EventBus;
import com.geek.fragmentdz.Bus.HistoryContainer;
import com.geek.fragmentdz.Bus.InfoContainer;
import com.geek.fragmentdz.WeatherData;
import com.geek.fragmentdz.InfoActivity;
import com.geek.fragmentdz.OnLoadListener;
import com.geek.fragmentdz.R;
import com.geek.fragmentdz.RVonClickListener;
import com.geek.fragmentdz.RecyclerDataAdapter;
import com.geek.fragmentdz.WeatherJsonData.WeatherDataController;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

public class FragmentList extends Fragment implements RVonClickListener, OnLoadListener {
    private RecyclerView recyclerView;
    private MaterialButton addCityBtn;
    private MaterialButton clearListBtn;
    private TextInputEditText cityName;

    private boolean orientationLandscape;
    private int currentPosition = 0;
    private int temperature;
    private double sunrise;
    private double sunset;
    private int clouds;
    private ArrayList<String> arr;
    private RecyclerDataAdapter adapter;
    private Pattern checkCityName = Pattern.compile("^[A-Z][a-z]{2,}$");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orientationLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        initViews(view);
        arr = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.cities)));
        initList();
        onBtnClickListener(view);
        checkTextField(view);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            temperature = savedInstanceState.getInt("temp");
            arr = savedInstanceState.getStringArrayList("data");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (orientationLandscape) {
            onItemClick(currentPosition);
        }
        initList();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("position", currentPosition);
        outState.putInt("temp", temperature);
        outState.putStringArrayList("data", arr);
        super.onSaveInstanceState(outState);
    }

    private void checkTextField(final View view) {
        cityName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) return;
                TextView tv = (TextView) v;
                String text = tv.getText().toString();
                if (checkCityName.matcher(text).matches()) {
                    tv.setError(null);
                } else {
                    tv.setError(getString(R.string.error_input_msg));
                }
            }
        });
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.cities_recycler_view);
        addCityBtn = view.findViewById(R.id.add_cities);
        clearListBtn = view.findViewById(R.id.clear_cities_btn);
        cityName = view.findViewById(R.id.city_edit_text);
    }

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new RecyclerDataAdapter(arr, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void createInfoFragment() {
        if (orientationLandscape) {
            EventBus.getBus().post(getInfo());
        } else {
            Intent intent = new Intent(getActivity(), InfoActivity.class);
            intent.putExtra("info", getInfo());
            startActivity(intent);
        }
    }

    private InfoContainer getInfo() {
        InfoContainer infoContainer = new InfoContainer();
        infoContainer.cityName = arr.get(currentPosition);
        infoContainer.currentPosition = this.currentPosition;
        infoContainer.temperature = this.temperature;
        infoContainer.sunrise = this.sunrise;
        infoContainer.sunset = this.sunset;
        infoContainer.clouds = this.clouds;
        return infoContainer;
    }

    @Override
    public void onItemClick(int position) {
        WeatherData getWeatherData = new WeatherData(arr.get(currentPosition), this);
        currentPosition = position;
    }

    @Override
    public void onReadyData(WeatherDataController weatherDataController) {
        temperature = weatherDataController.getMain().getTemp();
        sunrise = weatherDataController.getSys().getSunrise();
        sunset = weatherDataController.getSys().getSunset();
        clouds = weatherDataController.getClouds().getAll();
        String infoHistory = sendHistory();
        HistoryContainer.getInstance().addHistory(infoHistory);
        createInfoFragment();
    }

    private String sendHistory() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date(System.currentTimeMillis()));
        return time + ": " + arr.get(currentPosition) + getString(R.string.temp_is) + temperature;
    }

    private void onBtnClickListener(final View view) {
        addCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = cityName.getText().toString();
                if (!text.isEmpty()) {
                    adapter.add(text);
                    cityName.setText("");
                    String msg = getString(R.string.msg_string) + text + getString(R.string.msg_string2);
                    Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
                }
            }
        });
        clearListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, R.string.delete_cities, Snackbar.LENGTH_LONG).setAction(R.string.yes,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cityName.setText("");
                                adapter.clear();
                            }
                        }).show();
            }
        });
    }
}
