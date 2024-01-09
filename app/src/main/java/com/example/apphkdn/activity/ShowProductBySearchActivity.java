package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.linkGetProductBySearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.model.Product;

import java.util.ArrayList;

public class ShowProductBySearchActivity extends AppCompatActivity implements View.OnClickListener {
    RequestDB requestDB = new RequestDB();
    Button btnBack;
    TextView txtSearch;
    ImageButton imgbtnCart;
    RecyclerView recyclerView;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_by_search);

        initUI();
        settingActivity();
    }

    private void settingActivity(){
        getKeySearch();
        RecyleviewSetting();
        setText();
        onClick();
    }

    // Get search key from SearchActivity
    private String getKeySearch(){
        String key_search = getIntent().getStringExtra("keySearch");
        return key_search;
    }

    private void onClick(){
        txtSearch.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        imgbtnCart.setOnClickListener(this);
    }

    private void RecyleviewSetting(){
        productArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(),productArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(productAdapter);
        getDataForRCV();
    }

    // set data for recycleview
    private void getDataForRCV() {
        requestDB.GetProductBySearch(ShowProductBySearchActivity.this, productArrayList, productAdapter,linkGetProductBySearch + getKeySearch());
    }

    // set text for edittext
    private void setText(){
        txtSearch.setText(getKeySearch());
    }

    private void initUI(){
        txtSearch = findViewById(R.id.searchtxt_show_pdct_search);
        btnBack = findViewById(R.id.btn_search_product_back);
        recyclerView = findViewById(R.id.rcv_show_prd_search);
        imgbtnCart = findViewById(R.id.CartBtn_search);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.searchtxt_show_pdct_search){
            Intent intent = new Intent(ShowProductBySearchActivity.this, SearchActivity.class);
            intent.putExtra("searchtxt", txtSearch.getText().toString().trim());
            startActivity(intent);
        }
        if (v.getId() == R.id.btn_search_product_back){
            Intent intent = new Intent(ShowProductBySearchActivity.this,MainActivity.class);
            intent.putExtra("back_product_search",true);
            startActivity(intent);
        }
        if (v.getId() == R.id.CartBtn_search){
            Intent intent = new Intent(ShowProductBySearchActivity.this, CartActivity.class);
            startActivity(intent);
        }
    }
}