package com.example.apphkdn.activity;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apphkdn.R;
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
                linearLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.show_product_search_layout, new CategoryFragment()).commit();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Server.linkGetProductByCategory + getIdCategoryProduct(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    Integer id = 0;
                    String product_name="";
                    Integer product_price=0;
                    String product_image="";
                    String product_decs="";
                    Integer product_view=0;
                    Integer IDCategory=0;
                    for (int i =0; i<response.length();i++){
                        try {
                            JSONObject jsonObject= response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            product_name=jsonObject.getString("product_name");
                            product_price=jsonObject.getInt("product_price");
                            product_image=jsonObject.getString("product_image");
                            product_decs=jsonObject.getString("product_decs");
                            IDCategory=jsonObject.getInt("IDcategory");
                            productArrayList.add(new Product(id,product_name,product_image,product_decs,product_price,product_view,IDCategory));
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void initUI(){
        btnBack = findViewById(R.id.btn_search_product_back);
        recyclerView = findViewById(R.id.rcv_show_prd);
        linearLayout = findViewById(R.id.ln_delete);
    }
}
