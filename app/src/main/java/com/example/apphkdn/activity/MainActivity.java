package com.example.apphkdn.activity;

import static com.example.apphkdn.R.*;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apphkdn.R;
import com.example.apphkdn.adapter.CategoryAdapter;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.model.Category;
import com.example.apphkdn.model.Product;
import com.example.apphkdn.ultil.Checkconnection;
import com.example.apphkdn.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    TextView textView;
    DrawerLayout drawerLayout;
    ArrayList<Category> categories;
    CategoryAdapter categoryAdapter;
    int id =0;
    String Category_name="";
    String Category_image="";
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        anhxa();
        if (Checkconnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFiller();
            GetDataCategory();
            GetnewProduct();
        }else {
            Toast.makeText(MainActivity.this,"Vui lòng kiểm tra lại kết nối",Toast.LENGTH_SHORT).show();
            finish();
        }
        categories = new ArrayList<>();
        categories.add(0, new Category(0, "Trang Chính", "https://th.bing.com/th?id=OIP.O23pWqRhxVaXwaMtN1j9vQHaHa&w=250&h=250&c=8&rs=1&qlt=90&o=6&dpr=1.3&pid=3.1&rm=2"));
        categoryAdapter = new CategoryAdapter(categories, getApplicationContext());
        listView.setAdapter(categoryAdapter);



    }

    private void GetnewProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Server.linkNewProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    int id = 0;
                    String product_name="";
                    Integer product_price=0;
                    String product_image="";
                    String product_decs="";
                    int IDCategory=0;
                    for (int i =0; i<response.length();i++){
                        try {
                            JSONObject jsonObject= response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            product_name=jsonObject.getString("product_name");
                            product_price=jsonObject.getInt("product_price");
                            product_image=jsonObject.getString("product_image");
                            product_decs=jsonObject.getString("product_decs");
                            IDCategory=jsonObject.getInt("IDcategory");
                            productArrayList.add(new Product(id,product_name,product_image,product_decs,product_price,IDCategory));
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

    private void GetDataCategory() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.linkCategory, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response !=null){
                    for (int i =0;i<response.length();i++){
                        try {
                            JSONObject jsonObject= response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Category_name =jsonObject.getString("category_name");
                            Category_image=jsonObject.getString("category_image");
                            categories.add(new Category(id,Category_name,Category_image));
                            categoryAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Checkconnection.showToastShort(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFiller() {
        if (viewFlipper != null) {
            ArrayList<String> mangquangcao = new ArrayList<>();
            mangquangcao.add("https://salt.tikicdn.com/cache/w750/ts/tikimsp/24/ba/29/90d8cdefbf348cfd7bc53e6f1ca4c55c.jpg.webp");
            mangquangcao.add("https://salt.tikicdn.com/cache/w280/ts/tikimsp/bb/46/1d/495362fba97c4bfa89d79a85c5f0ebeb.png.webp");
            mangquangcao.add("https://salt.tikicdn.com/cache/w280/ts/tikimsp/5c/50/30/f7c0cbebc7a9a73236f6c85d9bcee091.png.webp");
            mangquangcao.add("https://salt.tikicdn.com/cache/w280/ts/tikimsp/5c/50/30/f7c0cbebc7a9a73236f6c85d9bcee091.png.webp");

            for (String imageUrl : mangquangcao) {
                ImageView imageView = new ImageView(this);
                Picasso.get().load(imageUrl).into(imageView);
                viewFlipper.addView(imageView);
            }

            // Set animation if needed
            viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
            viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);

            // Set flip interval
            viewFlipper.setFlipInterval(3000); // Change the interval as needed
            viewFlipper.setAutoStart(true);
        }
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhxa(){
        toolbar=findViewById(R.id.ToolBar);
        viewFlipper=findViewById(R.id.ViewFlipper);
        recyclerView=findViewById(R.id.RCV);
        navigationView=findViewById(R.id.NavigationVMHC);
        listView=findViewById(R.id.LVManHinhChinh);
        drawerLayout=findViewById(R.id.drawableLO);
        textView=findViewById(R.id.textchange);
        productArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(),productArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(productAdapter);
    }
}