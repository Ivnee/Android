package com.geek.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class NewPage extends AppCompatActivity {
    public static final String tempDataKey = "temp data key";
    public static final String colorDataKey = "colorDataKey";
    private static boolean checkWindSpeed = false;
    private int color;
    private TextView temp, today, tomorrow, date, windSpdView, checkCityView;
    private Button back, info;
    Random rd = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);
        initComponents();
        showReceivedData();
        windSpeedInfo();
        setTemp();
        setCity();
        addListenerForCheckInfo();
        addListenerToComeback();
    }

    public void initComponents() {
        temp = findViewById(R.id.temp);
        today = findViewById(R.id.today);
        info = findViewById(R.id.infoBtn);
        tomorrow = findViewById(R.id.tomorrow);
        date = findViewById(R.id.date);
        checkCityView = findViewById(R.id.city_intent);
        windSpdView = findViewById(R.id.wind_spd);
    }

    public void addListenerForCheckInfo() {
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = null;
                switch (checkCityView.getText().toString()) {
                    case ("Dmitrov"):
                        infoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.dmitrov_info)));
                        break;
                    case ("Moscow"):
                        infoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.moscow_info)));
                        break;
                    case ("Saint-Peterburg"):
                        infoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.spb_info)));
                        break;
                    case ("Sochi"):
                        infoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.sochi_info)));
                        break;
                    case ("Krasnodar"):
                        infoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.krasnodar_info)));
                        break;
                    default:
                        Toast.makeText(getBaseContext(), R.string.empty_city_info, Toast.LENGTH_SHORT).show();
                }
                startActivity(infoIntent);
            }
        });
    }

    public void addListenerToComeback() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tempIntent = new Intent();
                String tempText = String.valueOf(temp.getText());
                tempIntent.putExtra(tempDataKey, tempText);
                setResult(RESULT_OK, tempIntent);
                finish();
            }
        });
    }

    private void showReceivedData() {
        Intent checkIntent = getIntent();
        checkWindSpeed = checkIntent.getBooleanExtra(MainActivity.checkBoxKey, false);
    }

    @SuppressLint("SetTextI18n")
    private void windSpeedInfo() {
        if (checkWindSpeed) {
            int speed = rd.nextInt(10);
            windSpdView.setText(getString(R.string.wind_speed_msg) + String.valueOf(speed));

        }
    }

    private void setCity() {
        Intent cityTextIntent = getIntent();
        String cityName = cityTextIntent.getStringExtra(MainActivity.cityNameTextView);
        checkCityView.setText(cityName);
    }

    private void setTemp() {
        int t = rd.nextInt(60) - 30;
        if (t > 0) {
            String text = "+" + String.valueOf(t);
            temp.setText(text);
        } else {
            temp.setText(String.valueOf(t));
            color = ContextCompat.getColor(getBaseContext(), R.color.minusTempColor);
            temp.setTextColor(color);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        String text = temp.getText().toString();
        outState.putString(tempDataKey, text);
        //outState.putInt(colorDataKey, color); когда цвет восстанавливаю , иногда пропадает вьюха с температурой
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String text = savedInstanceState.getString(tempDataKey);
        temp.setText(text);
    /*    int textColor = savedInstanceState.getInt(colorDataKey);
        temp.setTextColor(textColor);
    */}
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }
}
