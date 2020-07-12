package com.geek.fragmentdz.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.fragmentdz.AppHistoryDB;
import com.geek.fragmentdz.Bus.EventBus;
import com.geek.fragmentdz.Bus.InfoContainer;
import com.geek.fragmentdz.History;
import com.geek.fragmentdz.HistoryDao;
import com.geek.fragmentdz.InfoActivity;
import com.geek.fragmentdz.OnSaveDataListener;
import com.geek.fragmentdz.ParsingWeatherData;
import com.geek.fragmentdz.R;
import com.geek.fragmentdz.RVonClickListener;
import com.geek.fragmentdz.RecyclerDataAdapter;
import com.geek.fragmentdz.citiesList.CitiesList;
import com.geek.fragmentdz.citiesList.CitiesListDao;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import static android.content.Context.LOCATION_SERVICE;

public class FragmentList extends Fragment implements RVonClickListener, OnSaveDataListener {
    private RecyclerView recyclerView;
    private MaterialButton addCityBtn;
    private MaterialButton clearListBtn;
    private MaterialButton myPositionBtn;
    private TextInputEditText cityName;
    private LocationManager mLocManager = null;

    private boolean orientationLandscape;
    private int currentPosition;
    private int temperature;
    private String currentCityName;
    private String keyForPrefPosition = "position";
    private ArrayList<String> arr;
    private ArrayList<CitiesList> arrCities;

    private RecyclerDataAdapter adapter;
    private Pattern checkCityName = Pattern.compile("^[A-Z][a-z]{2,}$");
    private ExecutorService executorService = Executors.newFixedThreadPool(3);
    private SharedPreferences prefs;
    private CitiesListDao citiesListDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("tag", "onCreate");
        return inflater.inflate(R.layout.list_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orientationLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        initViews(view);
        initShearedPrefs();
        onAddBtnClickListener(view);
        onClearBtnClickListener();
        onMyPosBtnClickListener();
        checkTextField();
        initCitiesDB();
    }

    private void initCitiesDB() {
        new Thread(() -> citiesListDao = AppHistoryDB.getInstance().getCitiesListDao()).start();
    }


    private void onMyPosBtnClickListener() {
        myPositionBtn.setOnClickListener(v -> checkPermissionAndGetMyLocation());
    }

    private void checkPermissionAndGetMyLocation() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            getMyLocation();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            boolean permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (permissionGranted) {
                getMyLocation();
            } else {
                Toast.makeText(getActivity(), "asd", Toast.LENGTH_SHORT).show();
                currentPosition = 0;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void getMyLocation() {
        mLocManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(LOCATION_SERVICE);
        Location loc;
        loc = Objects.requireNonNull(mLocManager).getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc != null) {
            Geocoder geo = new Geocoder(getActivity());
            List<Address> list = null;
            try {
                list = geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Objects.requireNonNull(list).isEmpty()) {
                Toast.makeText(getActivity(), R.string.place_not_found, Toast.LENGTH_SHORT).show();
            } else {
                Address a = list.get(0);
                currentCityName = a.getLocality();
                currentPosition = -1;
                ParsingWeatherData parsingWeatherData = new ParsingWeatherData(this, currentCityName, this.getActivity());
            }
        }
    }

    private void initShearedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext());
        currentPosition = prefs.getInt(keyForPrefPosition, 0);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            temperature = savedInstanceState.getInt("temp");
            arr = savedInstanceState.getStringArrayList("data");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Handler handler = new Handler();
        executorService.execute(() -> {
            citiesListDao = AppHistoryDB.getInstance().getCitiesListDao();
            ArrayList<CitiesList> arrayFromDB = (ArrayList<CitiesList>) citiesListDao.getFullCitiesList();
            arrCities = new ArrayList<>();
            if (!arrayFromDB.isEmpty()) {
                arrCities = arrayFromDB;
            } else {
                arr = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.cities)));
                for (int i = 0; i < arr.size(); i++) {
                    CitiesList citiesList = new CitiesList();
                    citiesList.cityName = arr.get(i);
                    arrCities.add(citiesList);
                    citiesListDao.insertCity(citiesList);
                }
            }
            handler.post(() -> {
                initList();
                if (orientationLandscape) {
                    onItemClick(currentPosition);
                }
            });
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("temp", temperature);
        outState.putStringArrayList("data", arr);
        super.onSaveInstanceState(outState);
    }

    private void checkTextField() {
        cityName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) return;
            TextView tv = (TextView) v;
            String text = tv.getText().toString();
            if (checkCityName.matcher(text).matches()) {
                tv.setError(null);
            } else {
                tv.setError(getString(R.string.error_input_msg));
            }
        });
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.cities_recycler_view);
        addCityBtn = view.findViewById(R.id.add_cities);
        clearListBtn = view.findViewById(R.id.clear_cities_btn);
        cityName = view.findViewById(R.id.city_edit_text);
        myPositionBtn = view.findViewById(R.id.my_place_btn);
    }

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new RecyclerDataAdapter(arrCities, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void createInfoFragment(InfoContainer infoContainer) {
        if (orientationLandscape) {
            EventBus.getBus().post(getInfo(infoContainer));
        } else {
            Intent intent = new Intent(getActivity(), InfoActivity.class);
            intent.putExtra(getString(R.string.key_for_info_fragment), getInfo(infoContainer));
            startActivity(intent);
        }
    }

    private InfoContainer getInfo(InfoContainer infoContainer) {
        infoContainer.currentPosition = this.currentPosition;
        return infoContainer;
    }

    @Override
    public void onItemClick(int position) {
        currentPosition = position;
        if (currentPosition >= 0) {
            currentCityName = arrCities.get(currentPosition).cityName;
            ParsingWeatherData parsingWeatherData = new ParsingWeatherData(this, currentCityName, this.getActivity());
        } else {
            checkPermissionAndGetMyLocation();
        }
    }

    @Override
    public void onClickSaveData(InfoContainer infoContainer) {
        temperature = infoContainer.temperature;
        insertHistoryDB();
        createInfoFragment(infoContainer);
    }

    private void insertHistoryDB() {
        executorService.execute(() -> {
            HistoryDao historyDao = AppHistoryDB.getInstance().getHistoryBuilderDB();
            History history = new History();
            history.cityName = currentCityName;
            String temp = String.valueOf(temperature);
            history.temperature = temp;
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String time = sdf.format(new Date(System.currentTimeMillis()));
            history.date = time;
            historyDao.insertHistory(history);
        });
    }


    private void onAddBtnClickListener(final View view) {
        addCityBtn.setOnClickListener(v -> {
            final String text = Objects.requireNonNull(cityName.getText()).toString();
            if (!text.isEmpty()) {
                Handler handler = new Handler();
                executorService.execute(() -> {
                    CitiesList cities = new CitiesList();
                    cities.cityName = text;
                    citiesListDao.insertCity(cities);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.add(cities);
                            cityName.setText("");
                        }
                    });
                });
                String msg = getString(R.string.msg_string) + text + getString(R.string.msg_string2);
                Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void onClearBtnClickListener() {
        clearListBtn.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            builder.setTitle(R.string.delete_cities).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    currentPosition = 0;
                    CitiesList citiesList = new CitiesList();
                    citiesList.cityName = "Moscow";
                    cityName.setText("");
                    adapter.clear();
                    new Thread(() -> {
                        citiesListDao.deleteCitiesList();
                        citiesListDao.insertCity(citiesList);
                    }).start();
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create().show();
        });
    }

    @Override
    public void onStop() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(keyForPrefPosition, currentPosition);
        editor.apply();
        super.onStop();
    }


}
