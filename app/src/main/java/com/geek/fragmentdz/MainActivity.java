package com.geek.fragmentdz;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.geek.fragmentdz.Bus.EventBus;
import com.geek.fragmentdz.Bus.InfoContainer;
import com.geek.fragmentdz.Fragments.FragmentInfo;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.otto.Subscribe;

import java.util.Objects;
import java.util.zip.Inflater;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.dev_info: {
                Toast.makeText(getBaseContext(), R.string.dev_text,Toast.LENGTH_SHORT).show();
                return true;
            }
            default:{
                return false;
            }
        }
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
