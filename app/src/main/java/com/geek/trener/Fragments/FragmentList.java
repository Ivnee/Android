package com.geek.trener.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.geek.trener.InfoActivity;
import com.geek.trener.InfoContainer;
import com.geek.trener.R;

import java.util.Objects;
import java.util.Random;

public class FragmentList extends Fragment {
    private ListView citiesListView;
    private TextView emptyTextView;

    private Random rd = new Random();
    private boolean orientationLand;
    private int currentPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initList();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orientationLand = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("index");
        }
        if (orientationLand){
            createInfoFragment(false);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("index", currentPosition);
        super.onSaveInstanceState(outState);
    }

    private void initList() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.cities, android.R.layout.simple_list_item_activated_1);
        citiesListView.setAdapter(adapter);
        citiesListView.setEmptyView(emptyTextView);

        citiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                createInfoFragment(true);
            }
        });
    }

    private void createInfoFragment(boolean setTemp) {
        if (orientationLand) {
            citiesListView.setItemChecked(currentPosition, true);
            FragmentInfo fragment = new FragmentInfo();
            if(fragment.getIndex() != currentPosition) {
                FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();
                ft.replace(R.id.info_box, FragmentInfo.setInfo(sendInfo(setTemp)));
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack("add");
                ft.commit();
            }
        }else{
            Intent intent = new Intent(getActivity(), InfoActivity.class);
            intent.putExtra("info",sendInfo(true));
            startActivity(intent);

        }
    }

    private void initViews(View view) {
        citiesListView = view.findViewById(R.id.cities_list_view);
        emptyTextView = view.findViewById(R.id.empty_text_for_list);
    }

    private InfoContainer sendInfo(Boolean setTemperature) {
        InfoContainer info = new InfoContainer();
        String[] cities = getResources().getStringArray(R.array.cities);
        info.cityName = cities[currentPosition];
        info.currentPosition = currentPosition;
        if(setTemperature) {
            info.temperature = rd.nextInt(50) - 25;
        }
        return info;
    }
}
