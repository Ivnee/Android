package com.geek.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String checkBoxKey = "checkBoxKey";
    public static String cityNameTextView = "cityName";
    private int requestCodeToOpenNewPage = 10;
    private Button ok, exit;
    private CheckBox windSpeedCheckBox;
    private EditText editCity;
    private Spinner cities;
    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        addListener();
    }

    private void initComponents() {
        ok = findViewById(R.id.ok);
        exit = findViewById(R.id.exit);
        windSpeedCheckBox = findViewById(R.id.checkWindSpeed);
        cities = findViewById(R.id.cities_spinner);
    }

    private void addListener() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.geek.test.NewPage");
                intent.putExtra(checkBoxKey, windSpeedCheckBox.isChecked());
                intent.putExtra(cityNameTextView, String.valueOf(cities.getSelectedItem()));
                startActivityForResult(intent, requestCodeToOpenNewPage);
                String cityMsg = getString(R.string.Info_about) + cities.getSelectedItem();
                Toast.makeText(getBaseContext(), cityMsg, Toast.LENGTH_SHORT).show();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCodeToOpenNewPage) {
            if (resultCode == RESULT_OK && data != null) {
                Toast.makeText(getBaseContext(), data.getStringExtra(NewPage.tempDataKey), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
