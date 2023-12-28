package com.example.apphkdn.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.apphkdn.R;
import com.google.android.material.navigation.NavigationView;

public class ShopSellerProductActivity extends AppCompatActivity{
    Toolbar toolbar;
    NavigationView settingnav;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_seller_product);
        UnitUI();

    }

    public void UnitUI(){
    }


}