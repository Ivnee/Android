package com.geek.firsthw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private boolean newText;
    private TextView firstMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setListenerOnBtn();
    }

    private void setListenerOnBtn() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newText){
                    firstMsg.setText("Is True");
                    newText = false;
                }else {
                    firstMsg.setText("Is False");
                    newText = true;
                }
            }
        });
    }

    public void initViews(){
        firstMsg= findViewById(R.id.textView);
        button = findViewById(R.id.button);
    }


}
