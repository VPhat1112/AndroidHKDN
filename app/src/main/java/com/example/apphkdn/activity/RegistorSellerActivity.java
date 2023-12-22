package com.example.apphkdn.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apphkdn.R;
import com.example.apphkdn.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegistorSellerActivity extends AppCompatActivity {

    EditText edtShopName, edtShopImg, edtShopAddress, edtShopKind, edtReason;
    Button btnSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registor_seller);
        initUI();
        Register();
    }

    private void Register(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtShopName.getText().toString().trim();
                String img = edtShopImg.getText().toString().trim();
                String address = edtShopAddress.getText().toString().trim();
                String kind = edtShopKind.getText().toString().trim();
                String Reason= edtReason.getText().toString().toString().trim();
                SharedPreferences preferences = getSharedPreferences("MyProfile", MODE_PRIVATE);
                String id_user = String.valueOf(preferences.getInt("id",0));
                new RegisterTask().execute(name,img,address,kind,id_user);
//                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showInvalidOtpDialog(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            TextView myMsg = new TextView(getApplicationContext());
        builder.setTitle("Register Seller");
        builder.setMessage(mess);

        // Adding a positive button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click if needed
                Intent intent = new Intent(RegistorSellerActivity.this,ShopActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // Must call show() prior to fetching text view
        TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

        TextView titleView = (TextView)alertDialog.findViewById(this.getResources().getIdentifier("alertTitle", "id", "android"));
        if (titleView != null) {
            titleView.setGravity(Gravity.CENTER);
        }
    }
    private class RegisterTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String ... params) {
            String name = params[0];
            String img = params[1];
            String address = params[2];
            String kind = params[3];
//            String reason=params[4];
            String id_User=params[4];

            try {
                URL url = new URL(Server.LinkRegistorSeller);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                // Prepare the data to be sent to the server
                String data = "NameShop=" + name + "&ImageShop=" + img + "&Address=" + address + "&KindShop=" + kind /*+ "&reason=" + reason*/+"&id_user=" + id_User;
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
                    String remess= "Đăng ký thành công! Xin đợi phản hồi từ admin";
                    showInvalidOtpDialog(remess);

                    // You can save the user details in SharedPreferences or other storage
                    // and navigate to the next activity
                } else {
                    // Login failed
//                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    String remess= "Đăng ký không thành công!";
                    showInvalidOtpDialog(remess);
                }

            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(LoginActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initUI(){
        edtShopName = findViewById(R.id.edt_shop_name);
        edtShopImg = findViewById(R.id.edt_img_seller);
        edtShopAddress = findViewById(R.id.edt_address_seller);
        edtShopKind = findViewById(R.id.edt_shop_kind);
        edtReason = findViewById(R.id.edt_reason_seller);
        btnSignUp = findViewById(R.id.btn_signup_seller);
    }
}
