package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.GetProductByShop;
import static com.example.apphkdn.ultil.Server.LinkGetShop;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.ProductAdapter;
import com.example.apphkdn.model.Product;
import com.example.apphkdn.ultil.DownloadImageTask;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    TextView tvShopName;
    ImageView imgShop;
    RecyclerView rcv;
    ArrayList<Product> productArrayListShop;
    ProductAdapter productAdapter;
    RequestDB requestDB = new RequestDB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        initUI();
        settingForShopActivity();
    }

    private void settingForShopActivity(){
        RecyleviewSetting();
        setNameShopAndImgShop();
    }

    private void setNameShopAndImgShop(){
        requestDB.GetShop(ShopActivity.this, getIdShop(), LinkGetShop);
        tvShopName.setText(DataLocalManager.getNameShop());
        String Shop_Image=DataLocalManager.getImageShop();
        new DownloadImageTask(imgShop).execute(Shop_Image);
    }

    // Get id shop from SearchActivity
    private String getIdShop(){
        String id = getIntent().getStringExtra("keyidShopcc");
        return id;
    }

    private void RecyleviewSetting(){
        productArrayListShop = new ArrayList<>();
        productAdapter = new ProductAdapter(ShopActivity.this,productArrayListShop);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShopActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new GridLayoutManager(ShopActivity.this,2));
        rcv.setAdapter(productAdapter);
        requestDB.GetProductShopDetail(ShopActivity.this,productArrayListShop,productAdapter,GetProductByShop+getIdShop());
    }

    private void initUI(){
        tvShopName = findViewById(R.id.fr_home_name);
        imgShop = findViewById(R.id.avtHome);
        rcv = findViewById(R.id.rcv_shop);
    }
}