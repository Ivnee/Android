package com.geek.fragmentdz.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.fragmentdz.Bus.EventBus;
import com.geek.fragmentdz.Bus.HistoryContainer;
import com.geek.fragmentdz.Bus.InfoContainer;
import com.geek.fragmentdz.InfoActivity;
import com.geek.fragmentdz.OnSaveDataListener;
import com.geek.fragmentdz.ParsingWeatherData;
import com.geek.fragmentdz.R;
import com.geek.fragmentdz.RVonClickListener;
import com.geek.fragmentdz.RecyclerDataAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

public class FragmentList extends Fragment implements RVonClickListener, OnSaveDataListener {
    private RecyclerView recyclerView;
    private MaterialButton addCityBtn;
    private MaterialButton clearListBtn;
    private TextInputEditText cityName;

    private boolean orientationLandscape;
    private int currentPosition = 0;
    private int temperature;
    private long sunrise;
    private long sunset;
    private int clouds;
    private int cod;
    private ArrayList<String> arr;
    private RecyclerDataAdapter adapter;
    private Pattern checkCityName = Pattern.compile("^[A-Z][a-z]{2,}$");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("tag","onCreate");
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
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
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
        infoContainer.cod = this.cod;
        return infoContainer;
    }

    @Override
    public void onItemClick(int position) {
        currentPosition = position;
        ParsingWeatherData parsingWeatherData = new ParsingWeatherData(this, arr.get(currentPosition));
    }

    @Override
    public void onClickSaveData(InfoContainer infoContainer) {
        temperature = infoContainer.temperature;
        sunrise = infoContainer.sunrise;
        sunset = infoContainer.sunset;
        clouds = infoContainer.clouds;
        cod = infoContainer.cod;
        HistoryContainer.getInstance().addHistory(sendHistory());
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
                final String text = cityName.getText().toString();
                if (!text.isEmpty()) {
                    adapter.add(text);
                    cityName.setText("");
                    String msg = getString(R.string.msg_string) + text + getString(R.string.msg_string2);
                    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        clearListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.delete_cities).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cityName.setText("");
                        adapter.clear();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
    }


}
