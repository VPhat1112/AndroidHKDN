package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.LinkGetUser;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShopSellerActivity extends AppCompatActivity {
    TextView Homeseller,MyProductseller,MyCategoryseller,Ordersseller,Logoutseller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_seller);
        UnitUI();
        EventAction();
    }
    private void EventAction(){
        Homeseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ShopSellerActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        MyProductseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ShopSellerActivity.this, ShopSellerProductActivity.class);
                startActivity(intent);
            }
        });
        MyCategoryseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent= new Intent(ShopSellerActivity.this,MainActivity.class);
//                startActivity(intent);
            }
        });

        Ordersseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopSellerActivity.this, SellerOrderActivity.class));
            }
        });
        Logoutseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("MyProfile", MODE_PRIVATE).edit().clear().apply();
                Intent intent = new Intent(ShopSellerActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private class GetUSerOrder extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            try {
                URL url = new URL(LinkGetUser);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                // Prepare the data to be sent to the server
                String data = "email=" + email;
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
                    // Login successful
                    int userId = jsonObject.getInt("id");
                    String Name = jsonObject.getString("Name");
                    String email = jsonObject.getString("email");
                    String Address = jsonObject.getString("Address");
                    int role=jsonObject.getInt("role");
                    String phone = jsonObject.getString("phone");
                    String Info_pay = jsonObject.getString("Info_pay");
                    String imgUS = jsonObject.getString("imgUS");

                    // You can save the user details in SharedPreferences or other storage
                    // and navigate to the next activity


                    DataLocalManager.setIdUser(userId);
                    DataLocalManager.setEmailUser(email);
                    DataLocalManager.setNameUser(Name);
                    DataLocalManager.setAddressUser(Address);
                    DataLocalManager.setRoleUser(role);
                    DataLocalManager.setPhoneUser(phone);
                    DataLocalManager.setInfoPayUser(Info_pay);
                    DataLocalManager.setImageUser(imgUS);
                    startActivity(new Intent(ShopSellerActivity.this, SellerOrderActivity.class));

                } else {
                    Toast.makeText(ShopSellerActivity.this, "Some thing ERROR", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ShopSellerActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void UnitUI(){
        Homeseller= findViewById(R.id.homeseller);
        MyProductseller=findViewById(R.id.Myproductseller);
        MyCategoryseller=findViewById(R.id.MyCategoryseller);
        Ordersseller=findViewById(R.id.Orderseller);
        Logoutseller=findViewById(R.id.Logoutseller);
    }


}