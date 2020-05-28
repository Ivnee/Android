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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String checkBoxKey = "checkBoxKey";
    public static String cityNameTextView = "cityName";
    private int requestCodeToOpenNewPage = 10;
    private Button ok, clear;
    private CheckBox windSpeedCheckBox;
    private EditText editCity;
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
        clear = findViewById(R.id.clear);
        windSpeedCheckBox = findViewById(R.id.checkWindSpeed);
        editCity = findViewById(R.id.find_city);
    }

    private void addListener() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.geek.test.NewPage");
                intent.putExtra(checkBoxKey, windSpeedCheckBox.isChecked());
                intent.putExtra(cityNameTextView,String.valueOf(editCity.getText()));
                startActivityForResult(intent, requestCodeToOpenNewPage);
                Toast.makeText(getBaseContext(), getString(R.string.Info_about) + editCity.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCity.setText("");
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

    /*

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, getString(R.string.on_start));
        Toast.makeText(getBaseContext(), getString(R.string.on_start), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, getString(R.string.on_stop));
        Toast.makeText(getBaseContext(), R.string.on_stop, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, getString(R.string.on_restart));
        Toast.makeText(getBaseContext(), R.string.on_restart, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, getString(R.string.on_resume));
        Toast.makeText(getBaseContext(), R.string.on_resume, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, getString(R.string.on_pause));
        Toast.makeText(getBaseContext(), R.string.on_pause, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, getString(R.string.on_destroy));
        Toast.makeText(getBaseContext(), R.string.on_destroy, Toast.LENGTH_SHORT).show();
    }
*/
}
