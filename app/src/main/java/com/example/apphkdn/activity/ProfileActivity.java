package com.example.apphkdn.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.apphkdn.R;

public class ProfileActivity extends AppCompatActivity {

    Button btnBack;
    CardView SignOut;
    TextView txtname,txtgmail,txtphonenumber,txtAddress,txtEmailAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initUI();
        getData();
        goBack();
        Signout();
    }
    private void getData(){
        SharedPreferences sharedPreferences= getSharedPreferences("MyProfile", MODE_PRIVATE);
        txtname.setText(sharedPreferences.getString("Name","not found"));
        txtgmail.setText(sharedPreferences.getString("email","not found"));
        txtAddress.setText("    "+sharedPreferences.getString("Address","not found"));
        txtphonenumber.setText("    "+sharedPreferences.getString("phone","not found"));
        txtEmailAd.setText("    "+sharedPreferences.getString("email","not found"));
    }

    private void goBack(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void Signout(){
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("MyProfile", MODE_PRIVATE).edit().clear().commit();
                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initUI(){
        SignOut=findViewById(R.id.prSignOut);
        btnBack = findViewById(R.id.btn_back_profile);
        txtname= findViewById(R.id.profile_fullname);
        txtgmail= findViewById(R.id.id_user);
        txtphonenumber= findViewById(R.id.prPhoneNumber);
        txtAddress= findViewById(R.id.prResidentialAddress);
        txtEmailAd=findViewById(R.id.prEmailAddress);
    }
}