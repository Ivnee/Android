package com.geek.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button ok,clear;
    private CheckBox windSpeed;
    private EditText editCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        addListener();
    }
    private void initComponents(){
     ok = findViewById(R.id.ok);
     clear = findViewById(R.id.clear);
     windSpeed = findViewById(R.id.checkWindSpeed);
     editCity = findViewById(R.id.find_city);
    }
    private void addListener(){
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.geek.test.NewPage");
                startActivity(intent);
                Toast.makeText(getBaseContext(),getString(R.string.Info_about) + editCity.getText(),Toast.LENGTH_SHORT ).show();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCity.setText("");
            }
        });
    }
}
