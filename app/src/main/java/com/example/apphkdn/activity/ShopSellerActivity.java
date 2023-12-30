package com.example.apphkdn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apphkdn.R;

public class ShopSellerActivity extends AppCompatActivity {
    TextView Homeseller,MyProductseller,MyCategoryseller,Ordersseller,Logoutseller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_seller);
        UnitUI();
        Homeseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ShopSellerActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        MyProductseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ShopSellerActivity.this, ShopSellerProductActivity.class);
                startActivity(intent);
            }
        });
        MyCategoryseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent= new Intent(ShopSellerActivity.this,MainActivity.class);
//                startActivity(intent);
            }
        });

        Ordersseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent= new Intent(ShopSellerActivity.this,MainActivity.class);
//                startActivity(intent);
            }
        });
        Logoutseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("MyProfile", MODE_PRIVATE).edit().clear().apply();
                Intent intent = new Intent(ShopSellerActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    public void UnitUI(){
        Homeseller= findViewById(R.id.homeseller);
        MyProductseller=findViewById(R.id.Myproductseller);
        MyCategoryseller=findViewById(R.id.MyCategoryseller);
        Ordersseller=findViewById(R.id.Orderseller);
        Logoutseller=findViewById(R.id.Logoutseller);
    }


}