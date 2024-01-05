package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.linkGetProductByCategory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.model.Product;

import java.util.ArrayList;

public class ShowProductByCategoryActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    RequestDB requestDB = new RequestDB();
    Button btnBack;
    TextView txtSearch;
    RecyclerView recyclerView;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_by_category);

        initUI();
        Back();
        setText();
        Search();
        RecyleviewSetting();
    }

    // Get id Category product
    private Integer getIdCategory(){
        Integer idCategory = getIntent().getIntExtra("id_category",-1);
        return idCategory;
    }

    private String getNameCategory(){
        String nameCategory = getIntent().getStringExtra("name_category");
        return nameCategory;
    }

    private void Search(){
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowProductByCategoryActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    // Return to Category Page
    private void Back(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linearLayout.setVisibility(View.GONE);
                //recyclerView.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("back_product_category",true);
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

    private void getDataForRCV() {
        requestDB.GetProduct(getApplicationContext(),productArrayList,productAdapter,linkGetProductByCategory + getIdCategory());
    }

    private void setText(){
        txtSearch.setText(getNameCategory());
    }

    private void initUI(){
        txtSearch = findViewById(R.id.searchtxt_show_pdct_category);
        btnBack = findViewById(R.id.btn_category_product_back);
        recyclerView = findViewById(R.id.rcv_show_prd_category);
        linearLayout = findViewById(R.id.ln_delete_category);
    }
}
