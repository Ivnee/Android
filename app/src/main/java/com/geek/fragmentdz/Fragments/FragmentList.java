package com.geek.fragmentdz.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.fragmentdz.Bus.EventBus;
import com.geek.fragmentdz.Bus.InfoContainer;
import com.geek.fragmentdz.InfoActyvity;
import com.geek.fragmentdz.R;
import com.geek.fragmentdz.RVonClickListener;
import com.geek.fragmentdz.RecyclerDataAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FragmentList extends Fragment implements RVonClickListener {
    private RecyclerView recyclerView;

    private boolean orientationLandscape;
    private int currentPosition;
    Random rd = new Random();

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
        initList();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");

//что я пытался сделать для сохранения данных при перевороте экрана(пробовал вставлять этот код перед созданием листа,после создания
            //createInfoFragment();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("position", currentPosition);
        super.onSaveInstanceState(outState);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.cities_recycler_view);
    }

    private void initList() {
        ArrayList<String > arr= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.cities)));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerDataAdapter adapter = new RecyclerDataAdapter(arr,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    private void createInfoFragment() {
        if (orientationLandscape) {
            EventBus.getBus().post(getInfo());
        } else {
            Intent intent = new Intent(getActivity(), InfoActyvity.class);
            intent.putExtra("info",getInfo());
            startActivity(intent);
        }
    }

    private InfoContainer getInfo() {
        InfoContainer infoContainer = new InfoContainer();
        String[] cities = getResources().getStringArray(R.array.cities);
        infoContainer.cityName = cities[currentPosition];
        infoContainer.currentPosition = currentPosition;
        infoContainer.temperature = rd.nextInt(50) -25;
        return infoContainer;
    }

    @Override
    public void onItemClick(int position) {
        currentPosition = position;
        createInfoFragment();
    }
}
