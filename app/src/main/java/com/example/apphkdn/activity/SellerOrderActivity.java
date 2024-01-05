package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.GetOrderApp;
import static com.example.apphkdn.ultil.Server.LinkGetShopByIdUser;
import static com.example.apphkdn.ultil.Server.LinkWaitOrder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.OrderAdapter;
import com.example.apphkdn.model.Order;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SellerOrderActivity extends AppCompatActivity {
    Button WaitOrder,ApproveOrder,CancelOrder,CompleteOrder;
    RecyclerView rcv_ShopOrder;
    ArrayList<Order> orderArrayList;
    OrderAdapter orderAdapter;
    int id_user = DataLocalManager.getIdUser();
    int id_shop;
    RequestDB requestDB = new RequestDB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_order);
        UnitUI();
        setDataWaitOrder();
        EventAction();
    }
    private void EventAction(){
        ApproveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderArrayList = new ArrayList<>();
                orderAdapter = new OrderAdapter(SellerOrderActivity.this,orderArrayList);
                rcv_ShopOrder.setHasFixedSize(true);
                rcv_ShopOrder.setLayoutManager(new GridLayoutManager(SellerOrderActivity.this,1));
                rcv_ShopOrder.setAdapter(orderAdapter);
                new GetOrderAP().execute(String.valueOf(id_user));
            }
        });
        WaitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderArrayList = new ArrayList<>();
                orderAdapter = new OrderAdapter(SellerOrderActivity.this,orderArrayList);
                rcv_ShopOrder.setHasFixedSize(true);
                rcv_ShopOrder.setLayoutManager(new GridLayoutManager(SellerOrderActivity.this,1));
                rcv_ShopOrder.setAdapter(orderAdapter);
                new GetOrder().execute(String.valueOf(id_user));
            }
        });
    }
    private void setDataWaitOrder(){
        orderArrayList = new ArrayList<>();
        orderAdapter = new OrderAdapter(SellerOrderActivity.this,orderArrayList);
        rcv_ShopOrder.setHasFixedSize(true);
        rcv_ShopOrder.setLayoutManager(new GridLayoutManager(SellerOrderActivity.this,1));
        rcv_ShopOrder.setAdapter(orderAdapter);
        new GetOrder().execute(String.valueOf(id_user));
    }
    private class GetOrderAP extends AsyncTask<String, Void, String> {

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
                    Log.d("id_shop", String.valueOf(id_shop));
                    DataLocalManager.setIdShopUser(id_shop);
                    requestDB.GetOrder(SellerOrderActivity.this,orderArrayList,orderAdapter,GetOrderApp+id_shop);
                } else {
                    Toast.makeText(SellerOrderActivity.this, "Some thing ERROR", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(SellerOrderActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetOrder extends AsyncTask<String, Void, String> {

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
                    Log.d("id_shop", String.valueOf(id_shop));
                    DataLocalManager.setIdShopUser(id_shop);
                    requestDB.GetOrder(SellerOrderActivity.this,orderArrayList,orderAdapter,LinkWaitOrder+id_shop);
                } else {
                    Toast.makeText(SellerOrderActivity.this, "Some thing ERROR", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(SellerOrderActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void UnitUI(){
        WaitOrder=findViewById(R.id.WaitOrder);
        ApproveOrder=findViewById(R.id.ApproveOrder);
        CancelOrder=findViewById(R.id.CancelOrder);
        CompleteOrder=findViewById(R.id.CompleteOrder);
        rcv_ShopOrder=findViewById(R.id.rcv_ShopOrder);
    }
}