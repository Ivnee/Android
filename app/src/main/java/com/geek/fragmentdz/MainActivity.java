package com.geek.fragmentdz;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.geek.fragmentdz.Bus.EventBus;
import com.geek.fragmentdz.Bus.InfoContainer;
import com.geek.fragmentdz.Fragments.FragmentInfo;
import com.geek.fragmentdz.Fragments.HistoryFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.otto.Subscribe;

import java.util.Objects;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigation();
    }

    private void initNavigation() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home,R.id.nav_history).setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this,R.id.fragment_list);
        NavigationUI.setupActionBarWithNavController(this,navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);
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
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragment_list);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                ||super.onSupportNavigateUp();
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
            Objects.requireNonNull(fragment).setData(container);
        }
    }
}
