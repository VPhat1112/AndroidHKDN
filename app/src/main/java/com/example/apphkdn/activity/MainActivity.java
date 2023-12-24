package com.example.apphkdn.activity;

import static com.example.apphkdn.R.layout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.apphkdn.R;
import com.example.apphkdn.adapter.ViewPagerAdapter;
import com.example.apphkdn.model.Category;
import com.example.apphkdn.ultil.Checkconnection;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    TextView textView;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        initUI();
        if (Checkconnection.haveNetworkConnection(getApplicationContext())){
            DisplayFragment();
            getResponseFromActivity();
        }else {
            Toast.makeText(MainActivity.this,"Vui lòng kiểm tra lại kết nối",Toast.LENGTH_SHORT).show();
            finish();
        }
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

    private void getResponseFromActivity(){
        if (getIntent().getBooleanExtra("back_product_category",false)){
            viewPager.setCurrentItem(1);
        } else if (getIntent().getBooleanExtra("back_product_search",false)) {
            viewPager.setCurrentItem(0);
        }
    }

    private void initUI(){
        viewPager=findViewById(R.id.view_page);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        toolbar=findViewById(R.id.ToolBar);
        drawerLayout=findViewById(R.id.drawableLO);
        textView=findViewById(R.id.textchange);
    }
}