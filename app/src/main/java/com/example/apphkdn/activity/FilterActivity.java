package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.LinkFilter;
import static com.example.apphkdn.ultil.Server.linkGetProductByCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.model.Product;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {

    RequestDB requestDB = new RequestDB();
    Button btnBack;
    TextView txtSearch;
    ImageButton imgbtnCart;
    RecyclerView rcv;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initUI();
        settingActivity();
    }

    private void settingActivity(){
        RecyleviewSetting();
        onClick();
    }

    private void onClick(){
        btnBack.setOnClickListener(this);
        txtSearch.setOnClickListener(this);
        imgbtnCart.setOnClickListener(this);
    }

    private void RecyleviewSetting(){
        productArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(),productArrayList);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        rcv.setAdapter(productAdapter);
        getDataForRCV();
    }

    private String getPriceLow(){
        String priceLow = getIntent().getStringExtra("str1");
        return priceLow;
    }

    private String getPriceHigh(){
        String priceHigh = getIntent().getStringExtra("str2");
        return priceHigh;
    }

    private void getDataForRCV() {
        requestDB.Filter(FilterActivity.this,productArrayList,productAdapter, getPriceLow(), getPriceHigh(), LinkFilter);
    }

    private void initUI(){
        btnBack = findViewById(R.id.btn_filter_product_back);
        txtSearch = findViewById(R.id.searchtxt_filter_search);
        imgbtnCart = findViewById(R.id.CartBtn_filter);
        rcv = findViewById(R.id.rcv_show_prd_filter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_filter_product_back){
            finish();
        }
        if (v.getId() == R.id.searchtxt_filter_search){
            Intent intent = new Intent(FilterActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.CartBtn_filter){
            Intent intent = new Intent(FilterActivity.this, CartActivity.class);
            startActivity(intent);
        }
    }
}