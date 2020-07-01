package com.geek.fragmentdz.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.fragmentdz.AppHistoryDB;
import com.geek.fragmentdz.History;
import com.geek.fragmentdz.HistoryDao;
import com.geek.fragmentdz.R;
import com.geek.fragmentdz.RecyclerHistoryAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {
    private RecyclerHistoryAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<History> arr;
    private Context context;
    private MaterialButton deleteHistoryBtn;
    private HistoryDao historyDao;


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
        deleteHistoryBtn = view.findViewById(R.id.delete_history);
        initList();
        setOnDeleteBtnListener();
    }

    private void setOnDeleteBtnListener() {

        deleteHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(()->{
                    historyDao.deleteHistory();
                }).start();

                adapter.clear();
            }
        });
    }

    private void initList() {
        new Thread(() -> {
            historyDao = AppHistoryDB.getInstance().getHistoryBuilderDB();
            arr = (ArrayList<History>) historyDao.getFullHistory();
            LinearLayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,true);
            adapter = new RecyclerHistoryAdapter(arr);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }).start();
    }
}
