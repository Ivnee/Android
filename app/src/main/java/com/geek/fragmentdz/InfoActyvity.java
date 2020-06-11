package com.geek.fragmentdz;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geek.fragmentdz.Bus.InfoContainer;
import com.geek.fragmentdz.Fragments.FragmentInfo;

public class InfoActyvity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setInfoFragment();
        if (isLandscapeOrientation()) {
            finish();
            return;
        }
    }

    private boolean isLandscapeOrientation() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void setInfoFragment() {
        Intent intent = getIntent();
        InfoContainer infoContainer = (InfoContainer) intent.getSerializableExtra("info");
        FragmentInfo fragment = (FragmentInfo) getSupportFragmentManager().findFragmentById(R.id.fragment_info_intent);
        if (fragment != null) {
            fragment.setData(infoContainer.cityName, infoContainer.temperature, infoContainer.currentPosition);
        }
    }

}
