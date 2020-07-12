package com.geek.fragmentdz.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.fragmentdz.Bus.InfoContainer;
import com.geek.fragmentdz.DialogErrorFragment;
import com.geek.fragmentdz.R;
import com.geek.fragmentdz.RVWeatherContainer;
import com.geek.fragmentdz.RVonClickListener;
import com.geek.fragmentdz.RecycleTimeWeatherAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class FragmentInfo extends Fragment implements RVonClickListener {
    private final String serverClientId =
            "911453222997-sk9uearal8f7t235r354p4l70a0rhuaf.apps.googleusercontent.com";
    private final int RC_SIGN_IN = 100;
    private final String TAG = "Google SignIn";
    private TextView cityName, temperature, date;
    private SharedPreferences prefs;
    private FrameLayout containImage;
    private RecyclerView history;
    private MaterialButton info;
    private GoogleSignInClient googleSignInClient;
    private SignInButton signInButton;

    private int currentPosition = -10;
    private long sunrise;
    private long sunset;
    private int clouds;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(serverClientId)
                .requestServerAuthCode(serverClientId,false)
                .build();
        googleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getActivity()), gso);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        containImage = view.findViewById(R.id.contain_image);
        initViews(view);
        setInfoBtnListener();
        setOnSignInListenerBtn();
    }

    private void setOnSignInListenerBtn() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String token = Objects.requireNonNull(account).getIdToken();
            if(token!= null){
                Toast.makeText(getActivity(),TAG,Toast.LENGTH_SHORT).show();
            }

        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void setData(InfoContainer container) {
        if (this.currentPosition != container.currentPosition) {
            int cod = container.cod;
            if (cod != 200) {
                DialogErrorFragment def = new DialogErrorFragment(cod);
                def.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), getString(R.string.dialog_err_tag));
            }
            this.currentPosition = container.currentPosition;
            this.cityName.setText(container.cityName);
            this.sunrise = container.sunrise;
            this.sunset = container.sunset;
            this.clouds = container.clouds;
            initTemperature(container.temperature);
            createWeatherImg(sunrise, sunset, clouds);
            initWeatherRV();
            initDate();
        }
    }


    private void initWeatherRV() {
        ArrayList<RVWeatherContainer> arr = new ArrayList<RVWeatherContainer>(Arrays.asList
                (new RVWeatherContainer("15:00", requireActivity().getDrawable(R.drawable.icon1), "+24"),
                        new RVWeatherContainer("16:00", requireActivity().getDrawable(R.drawable.icon1), "+22"),
                        new RVWeatherContainer("17:00", requireActivity().getDrawable(R.drawable.icon2), "+21"),
                        new RVWeatherContainer("18:00", requireActivity().getDrawable(R.drawable.icon2), "+19"),
                        new RVWeatherContainer("19:00", requireActivity().getDrawable(R.drawable.icon3), "+18"),
                        new RVWeatherContainer("20:00", requireActivity().getDrawable(R.drawable.icon3), "+16"),
                        new RVWeatherContainer("21:00", requireActivity().getDrawable(R.drawable.icon4), "+15")));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecycleTimeWeatherAdapter adapter = new RecycleTimeWeatherAdapter(arr);
        history.setLayoutManager(layoutManager);
        history.setAdapter(adapter);
    }

    private void initDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateText = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        date.setText(dateText);
    }

    private void initTemperature(int temp) {
        int tempColor;
        if (temp > 0) {
            String t = "+" + temp;
            temperature.setText(t);
            tempColor = ContextCompat.getColor(requireActivity(), R.color.yellow);
            temperature.setTextColor(tempColor);
        } else {
            String t = String.valueOf(temp);
            temperature.setText(t);
            tempColor = ContextCompat.getColor(requireActivity(), R.color.colorPrimary);
            temperature.setTextColor(tempColor);
        }
    }

    private void createWeatherImg(double sunrise, double sunset, int clouds) {
        long currentTime = new Date().getTime();
        containImage.removeAllViews();
        final ImageView img = new ImageView(getActivity());
        if (currentTime >= (sunrise * 1000) && currentTime < (sunset * 1000)) {
            setPicassoImg("http://pngimg.com/uploads/sun/sun_PNG13414.png", img);
            if (clouds >= 20 & clouds < 40) {
                setPicassoImg("http://pngimg.com/uploads/sun/sun_PNG13411.png", img);
            } else if (clouds >= 40 && clouds < 55) {
                img.setImageResource(R.drawable.clouds);
            } else if (clouds >= 55) {
                setPicassoImg("http://pngimg.com/uploads/cloud/cloud_PNG31.png", img);
            }
        } else {
            img.setImageResource(R.drawable.moon);
            if (clouds >= 30) {
                setPicassoImg("http://pngimg.com/uploads/cloud/cloud_PNG31.png", img);
            }
        }
        containImage.addView(img);
    }

    private void setPicassoImg(String url, ImageView img) {
        Picasso.get().load(url)
                .placeholder(R.drawable.load)
                .into(img);
    }

    private void initViews(View view) {
        info = view.findViewById(R.id.info_btn);
        history = view.findViewById(R.id.time_weather_list);
        cityName = view.findViewById(R.id.cityname_text_view);
        date = view.findViewById(R.id.date_text_view);
        temperature = view.findViewById(R.id.temp_text_view);
        containImage = view.findViewById(R.id.contain_image);
        signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
    }

    @Override
    public void onItemClick(int position) {
        System.out.println(position);
    }

    private void setInfoBtnListener() {
        info.setOnClickListener(v -> {
            StringBuilder sb = new StringBuilder("https://yandex.ru/pogoda/");
            sb.append(cityName.getText());
            Uri url = Uri.parse(sb.toString());
            Intent intent = new Intent(Intent.ACTION_VIEW, url);
            startActivity(intent);
        });
    }


}
