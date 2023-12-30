package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.GetProductByShop;
import static com.example.apphkdn.ultil.Server.LinkGetShopByIdUser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.ProductShopAdapter;
import com.example.apphkdn.model.Product;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShopSellerProductActivity extends AppCompatActivity{
    TextView Btn_Back_Product,Addproduct;
    RecyclerView product_shop_rcv;
    RequestDB requestDB = new RequestDB();

    ArrayList<Product> productArrayListSeller;
    ProductShopAdapter productShopAdapter;
    int id_shop,shop_rate,status;
    int id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_seller_product);
        UnitUI();
        EventAction();
        SetMyProduct();

    }
    private void EventAction(){
        Btn_Back_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopSellerProductActivity.this, ActivitySellerAddProduct.class));
            }
        });
    }
    private void SetMyProduct(){
        productArrayListSeller = new ArrayList<>();
        productShopAdapter = new ProductShopAdapter(ShopSellerProductActivity.this,productArrayListSeller);
        product_shop_rcv.setHasFixedSize(true);
        product_shop_rcv.setLayoutManager(new GridLayoutManager(ShopSellerProductActivity.this,1));
        product_shop_rcv.setAdapter(productShopAdapter);
        GetMyProduct();
    }
    private void GetMyProduct(){
        SharedPreferences preferences = getSharedPreferences("MyProfile", MODE_PRIVATE);
        int id_user = preferences.getInt("id",-1);
        new GetShopSeller().execute(String.valueOf(id_user));
    }

    private class GetShopSeller extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String id_user = params[0];
            try {
                URL url = new URL(LinkGetShopByIdUser);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                // Prepare the data to be sent to the server
                String data = "id_user=" + id_user;
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                // Get the response from the server
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line).append("\n");
                }
                br.close();

                return response.toString().trim();

            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                boolean success = jsonObject.getBoolean("success");

                if (success) {
                    id_shop=jsonObject.getInt("id");
                    SharedPreferences preferences = getSharedPreferences("MyProfile", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("id_shop", id_shop);
                    editor.apply();
                    requestDB.GetProductShop(ShopSellerProductActivity.this,productArrayListSeller,productShopAdapter,GetProductByShop+id_shop);
                } else {
                    Toast.makeText(ShopSellerProductActivity.this, "Some thing ERROR", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ShopSellerProductActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void UnitUI(){
        Btn_Back_Product=findViewById(R.id.Btn_Back_Product);
        Addproduct=findViewById(R.id.Addproduct);
        product_shop_rcv=findViewById(R.id.product_shop_rcv);

    }


}