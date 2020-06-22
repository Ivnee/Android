package com.geek.fragmentdz.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geek.fragmentdz.Bus.HistoryContainer;
import com.geek.fragmentdz.R;

import java.util.ArrayList;

import static com.geek.fragmentdz.Bus.HistoryContainer.getInstance;

public class HistoryFragment extends Fragment {/*
    private RecyclerHistoryAdapter adapter;
    private RecyclerView recyclerView;*/
    private ArrayList<String> arr;
    private TextView historyText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyText = view.findViewById(R.id.gistory_text_view);
        initHistory();

    }

    private void initHistory() {
        arr = HistoryContainer.getInstance().getArr();
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s).append("\n").append(getString(R.string.divider)).append("\n");
        }
        historyText.setText(sb);
    }

    @Override
    public void onResume() {
        super.onResume();
    }




}
