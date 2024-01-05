package com.example.apphkdn.activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apphkdn.R;

public class MyOrderActivity extends AppCompatActivity {
    ListView ChooseStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        UnitUI();
    }
    private void UnitUI(){
        ChooseStatus=findViewById(R.id.ChooseStatus);
    }
}