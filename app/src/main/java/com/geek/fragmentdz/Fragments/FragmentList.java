package com.geek.fragmentdz.Fragments;

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

import com.geek.fragmentdz.Bus.EventBus;
import com.geek.fragmentdz.InfoActyvity;
import com.geek.fragmentdz.Bus.InfoContainer;
import com.geek.fragmentdz.R;

import java.util.Objects;
import java.util.Random;

public class FragmentList extends Fragment {
    ListView list;
    TextView emptyText;

    boolean orientationLandscape;
    int currentPosition;
    Random rd = new Random();

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
        orientationLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
        }
      /*  if(orientationLandscape){
            EventBus.getBus().post(getInfo());
        }*/
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("position", currentPosition);
        super.onSaveInstanceState(outState);
    }

    private void initViews(View view) {
        list = view.findViewById(R.id.cities_list_view);
        emptyText = view.findViewById(R.id.empty_text_view);
    }

    private void initList() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.cities, android.R.layout.simple_list_item_activated_1);
        list.setAdapter(adapter);
        list.setEmptyView(emptyText);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                createInfoFragment();
            }
        });
    }

    private void createInfoFragment() {
        if (orientationLandscape) {
            list.setItemChecked(currentPosition, true);
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
}
