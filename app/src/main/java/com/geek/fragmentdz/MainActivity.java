package com.geek.fragmentdz;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.geek.fragmentdz.Bus.EventBus;
import com.geek.fragmentdz.Bus.InfoContainer;
import com.geek.fragmentdz.Fragments.FragmentInfo;
import com.squareup.otto.Subscribe;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getBus().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getBus().unregister(this);
        super.onStop();
    }
    private boolean isLandscapeOrientation(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Subscribe
    public void setInfoFragment(InfoContainer container) {
        if (isLandscapeOrientation() ) {
            FragmentInfo fragment = (FragmentInfo) getSupportFragmentManager().findFragmentById(R.id.fragment_info);
            Objects.requireNonNull(fragment).setData(container.cityName, container.temperature, container.currentPosition);
        }
    }
}
