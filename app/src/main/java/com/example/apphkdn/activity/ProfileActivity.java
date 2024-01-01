package com.example.apphkdn.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
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
//        SharedPreferences sharedPreferences= getSharedPreferences("MyProfile", MODE_PRIVATE);
        txtname.setText(DataLocalManager.getNameUser());
        txtgmail.setText(DataLocalManager.getEmailUser());
        txtAddress.setText("    "+DataLocalManager.getAddressUser());
        txtphonenumber.setText("    "+DataLocalManager.getPhoneUser());
        txtEmailAd.setText("    "+DataLocalManager.getEmailUser());
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
                DataLocalManager.ClearMySharedPreferences();
                Log.d("Debug", DataLocalManager.getEmailUser());
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