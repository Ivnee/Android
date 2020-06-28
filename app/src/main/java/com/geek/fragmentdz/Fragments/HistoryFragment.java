package com.geek.fragmentdz.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.fragmentdz.Bus.HistoryContainer;
import com.geek.fragmentdz.R;
import com.geek.fragmentdz.RecyclerHistoryAdapter;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {
    private RecyclerHistoryAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<String> arr;
    private Context context;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.history_recycler_view);
        initList();
    }

    private void initList() {
        arr = HistoryContainer.getInstance().getArr();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        adapter = new RecyclerHistoryAdapter(arr);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
