package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.linkGetProductBySearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.model.Product;

import java.util.ArrayList;

public class ShowProductBySearchActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    RequestDB requestDB = new RequestDB();
    Button btnBack;
    TextView txtSearch;
    RecyclerView recyclerView;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_by_search);

        initUI();
        getKeySearch();
        RecyleviewSetting();
        Back();
        setText();
        Search();
    }

    // Get search key from SearchActivity
    private String getKeySearch(){
        String key_search = getIntent().getStringExtra("keySearch");
        return key_search;
    }

    private void Search(){
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowProductBySearchActivity.this, SearchActivity.class);
                intent.putExtra("searchtxt", txtSearch.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    // Return to Home page
    private void Back(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linearLayout.setVisibility(View.GONE);
                //recyclerView.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("back_product_search",true);
                startActivity(intent);
            }
        });
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
        linearLayout = findViewById(R.id.ln_delete_search);
    }
}