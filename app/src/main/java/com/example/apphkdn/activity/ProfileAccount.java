package com.example.apphkdn.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.apphkdn.R;
import com.example.apphkdn.adapter.SettingInfoAdapter;
import com.example.apphkdn.ultil.Checkconnection;
import com.google.android.material.navigation.NavigationView;

public class ProfileAccount extends AppCompatActivity {
    Toolbar setting;
    ListView listView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_account);
        initUI();
        if (Checkconnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
        }else {
            Toast.makeText(ProfileAccount.this,"Vui lòng kiểm tra lại kết nối",Toast.LENGTH_SHORT).show();
            finish();
        }

        String[] data = {"Đến trang của tôi", "Chỉnh sửa thông tin"};
        int[] imageResources = {
                R.drawable.baseline_person_pin_24,
                R.drawable.outline_edit_24,
        };

        SettingInfoAdapter adapter = new SettingInfoAdapter(this, data, imageResources);
        listView.setAdapter(adapter);
    }
    private void ActionBar() {
        setSupportActionBar(setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setting.setNavigationIcon(R.drawable.outline_settings_24);
        setting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    public void initUI(){
        setting=findViewById(R.id.ToolBarSetting);
        drawerLayout=findViewById(R.id.drawableInf);
        listView=findViewById(R.id.LVSetting);
        navigationView=findViewById(R.id.NavigationSetting);
    }
}