package com.example.apphkdn.activity;

import static com.example.apphkdn.R.layout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apphkdn.R;
import com.example.apphkdn.adapter.CategoryAdapter;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.adapter.ViewPagerAdapter;
import com.example.apphkdn.model.Category;
import com.example.apphkdn.model.Product;
import com.example.apphkdn.ultil.Checkconnection;
import com.example.apphkdn.ultil.Server;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    ListView listView;
    TextView textView;
    DrawerLayout drawerLayout;
    ArrayList<Category> categories;
    CategoryAdapter categoryAdapter;
    int id =0;
    String Category_name="";
    String Category_image="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        anhxa();
        if (Checkconnection.haveNetworkConnection(getApplicationContext())){
            DisplayFragment();
            //GetDataCategory();
        }else {
            Toast.makeText(MainActivity.this,"Vui lòng kiểm tra lại kết nối",Toast.LENGTH_SHORT).show();
            finish();
        }



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

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void DisplayFragment(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_tab_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_tab_category).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_tab_news).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_tab_user).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_tab_home){
                    viewPager.setCurrentItem(0);
                } else if (item.getItemId()==R.id.menu_tab_category) {
                    viewPager.setCurrentItem(1);
                } else if (item.getItemId()==R.id.menu_tab_news) {
                    viewPager.setCurrentItem(2);
                } else {
                    viewPager.setCurrentItem(3);
                }
                return true;
            }
        });
    }

    private void anhxa(){
        viewPager=findViewById(R.id.view_page);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        toolbar=findViewById(R.id.ToolBar);
        drawerLayout=findViewById(R.id.drawableLO);
        textView=findViewById(R.id.textchange);
    }
}