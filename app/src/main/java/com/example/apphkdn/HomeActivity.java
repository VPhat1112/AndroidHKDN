package com.example.apphkdn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.activity.LoginActivity;

public class HomeActivity extends AppCompatActivity {

    Button Startbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DataLocalManager.init(getApplicationContext());
        anhxa();

        Startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public void anhxa(){
        Startbtn=findViewById(R.id.buttonStart);
    }
}