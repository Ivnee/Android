package com.geek.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class NewPage extends AppCompatActivity {
    private TextView temp,today,tomorrow,date;
    private Button back;
    private Intent mainIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);
        initComponents();
    }
    public void initComponents(){
        temp = findViewById(R.id.temp);
        today = findViewById(R.id.today);
        tomorrow = findViewById(R.id.tomorrow);
        date = findViewById(R.id.date);
        mainIntent = new Intent(".MainActivity");
    }
}
