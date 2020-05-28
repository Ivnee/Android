package com.geek.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class NewPage extends AppCompatActivity {
    public static final String tempDataKey = "temp data key";
    private static boolean checkWindSpeed = false;
    private TextView temp, today, tomorrow, date, windSpdView, checkCityView;
    private Button back,info;
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
    public void addListenerForCheckInfo(){
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://in-dmitrov.ru/"));
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

    private void setCity(){
        Intent cityTextIntent = getIntent();
        String cityName = cityTextIntent.getStringExtra(MainActivity.cityNameTextView);
        checkCityView.setText(cityName);
    }

    private void setTemp() {
        int t = rd.nextInt(40) - 20;
        if (t > 0) {
            int color = ContextCompat.getColor(getBaseContext(), R.color.tempColor);
            temp.setText("+" + String.valueOf(t));
        } else {
            temp.setText(String.valueOf(t));
            int color = ContextCompat.getColor(getBaseContext(), R.color.minusTempColor);
            temp.setTextColor(color);
        }
    }
}
