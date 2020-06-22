package com.geek.fragmentdz.Bus;

import java.util.ArrayList;

public final class HistoryContainer {
    private static HistoryContainer instance = null;
    private ArrayList<String> arr = new ArrayList<>();

    public static HistoryContainer getInstance() {
        if (instance == null) {
            instance = new HistoryContainer();
        }
        return instance;
    }

    public ArrayList<String> getArr() {
        return arr;
    }

    public void addHistory(String text) {
        arr.add(text);
    }
}
