package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.linkGetProductByCategory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.fragment.CategoryFragment;
import com.example.apphkdn.model.Product;
import com.example.apphkdn.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowProductSearchActivity extends AppCompatActivity {
    String key_search;
    LinearLayout linearLayout;
    RequestDB requestDB = new RequestDB();
    Button btnBack;
    RecyclerView recyclerView;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_product_search);

        getKeySearch();
        initUI();
        Back();
        RecyleviewSetting();
        GetProductByCategory();
    }

    // Get search key from SearchActivity
    private void getKeySearch(){
        key_search = getIntent().getStringExtra("key_search");
    }

    // Get id Category product
    private Integer getIdCategoryProduct(){
        Integer idCategory = getIntent().getIntExtra("id_category",-1);
        return idCategory;
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
    }

    private void GetProductByCategory() {
        requestDB.GetProduct(getApplicationContext(),productArrayList,productAdapter,linkGetProductByCategory + getIdCategoryProduct());
    }

    private void initUI(){
        btnBack = findViewById(R.id.btn_search_product_back);
        recyclerView = findViewById(R.id.rcv_show_prd);
        linearLayout = findViewById(R.id.ln_delete);
    }
}
