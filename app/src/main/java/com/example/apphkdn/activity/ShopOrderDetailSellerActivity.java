package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.AcceptOrder;
import static com.example.apphkdn.ultil.Server.getProductOder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.product_Order_adapter;
import com.example.apphkdn.model.order_product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopOrderDetailSellerActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton back_Btn_Detail, btn_danhgia;
    ImageView img_producr_buyer_order;
    RecyclerView rcv_productOrder;
    TextView txtOrder_ID,txtCreatedAt,StatusDetail,tv_name_buyer_order,txtSDTDetail,txtAddress, edit_accept_product, edit_deni_product;
    Integer Order_Id,Status,Price,Quantity,Shop_id,id,Product_price;
    String CreatedAt,NameBuyer,SDT,Address,Name_Shop,Product_Name,Image,shop_image,user_name;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    RequestDB requestDB = new RequestDB();
    product_Order_adapter productOrderAdapter;
    ArrayList<order_product> orderProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_order_detail_for_seller);
        UnitUI();
        GetData();
        setData();
        EventAction();
        SetRCV();
    }
    private void EventAction(){
        back_Btn_Detail.setOnClickListener(this);
        edit_accept_product.setOnClickListener(this);
        edit_deni_product.setOnClickListener(this);
    }

    private void GetData(){
        //GetData
        id= Integer.valueOf(getIntent().getStringExtra("ids"));
        Shop_id= Integer.valueOf(getIntent().getStringExtra("Shop_ids"));
        CreatedAt= getIntent().getStringExtra("CreatedAts");
        Status= Integer.valueOf(getIntent().getStringExtra("statuss"));
        SDT=getIntent().getStringExtra("SDTs");
        Address=getIntent().getStringExtra("Address_ships");
        Name_Shop=getIntent().getStringExtra("shopNames");
        shop_image=getIntent().getStringExtra("shop_images");
        Product_price= Integer.valueOf(getIntent().getStringExtra("Product_prices"));
        user_name=getIntent().getStringExtra("user_names");
    }
    private void setData(){
        txtOrder_ID.setText(String.valueOf(id));
        txtCreatedAt.setText(CreatedAt);
        if (Status==0){
            StatusDetail.setText("Đang chờ duyệt");
        }else if(Status==1){
            StatusDetail.setText("Đang Giao");
        }else if(Status==2){
            StatusDetail.setText("Đã hoàn thành");
        }else if(Status==3){
            StatusDetail.setText("Đã Hủy");
        }
        tv_name_buyer_order.setText(user_name);
        txtSDTDetail.setText(SDT);
        txtAddress.setText(Address);
    }
    private void SetRCV(){
        orderProducts = new ArrayList<>();
        productOrderAdapter = new product_Order_adapter(ShopOrderDetailSellerActivity.this,orderProducts);
        rcv_productOrder.setHasFixedSize(true);
        rcv_productOrder.setLayoutManager(new GridLayoutManager(ShopOrderDetailSellerActivity.this,1));
        rcv_productOrder.setAdapter(productOrderAdapter);
        requestDB.GetProductOrder(ShopOrderDetailSellerActivity.this,orderProducts,productOrderAdapter,getProductOder+String.valueOf(id));
    }

    private void UnitUI(){
        back_Btn_Detail=findViewById(R.id.back_Btn_Details);
        txtOrder_ID=findViewById(R.id.txtOrder_IDs);
        txtCreatedAt=findViewById(R.id.txtCreatedAts);
        StatusDetail=findViewById(R.id.StatusDetails);
        tv_name_buyer_order=findViewById(R.id.tv_name_buyer_orders);
        txtSDTDetail=findViewById(R.id.txtSDTDetails);
        txtAddress=findViewById(R.id.txtAddresss);
        rcv_productOrder=findViewById(R.id.rcv_product_order_details);
        edit_accept_product=findViewById(R.id.edit_accept_product);
        edit_deni_product=findViewById(R.id.edit_deni_product);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_Btn_Details){
            finish();
        }

        //+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_

        if (v.getId() == R.id.edit_accept_product){
            String order_id = String.valueOf(id);
            String mess="";
            int request=0;
            AlertDialog.Builder builder = new AlertDialog.Builder(ShopOrderDetailSellerActivity.this);
            if (Status==0){
                mess = "Bạn có muốn nhận đơn hàng!!!";
                builder.setTitle("Accept Order");
                builder.setMessage(mess);
                request=1;
            } else if (Status==1) {
                mess = "Bạn có muốn xác nhận đơn hàng đã hoàn thành!!!";
                builder.setTitle("Complete Order");
                builder.setMessage(mess);
                request=2;
            }
            String CheckRQ=String.valueOf(request);


            // Adding a positive button click listener
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Handle the "OK" button click if needed
                    RequestQueue queue = Volley.newRequestQueue(ShopOrderDetailSellerActivity.this);
                    String url = AcceptOrder;

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //Log.d("Tasas",response);
                                    if (response=="success"){
                                        String remess= "thành công!";
                                        RequestDB.showInvalidOtpDialogAcceptOrder(ShopOrderDetailSellerActivity.this,remess);
                                    }else if (response.equals("failed")){
                                        String remess= "thất bại!";
                                        RequestDB.showInvalidOtpDialogERROR(ShopOrderDetailSellerActivity.this,remess);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ShopOrderDetailSellerActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String,String> params = new HashMap<>();
                            params.put("order_id",order_id);
                            params.put("request",CheckRQ);
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            // Must call show() prior to fetching text view
            TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
            messageView.setGravity(Gravity.CENTER);

            TextView titleView = (TextView)alertDialog.findViewById(ShopOrderDetailSellerActivity.this.getResources().getIdentifier("alertTitle", "id", "android"));
            if (titleView != null) {
                titleView.setGravity(Gravity.CENTER);
            }
        }

        //+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_

        if (v.getId() == R.id.edit_deni_product){
            String order_id = String.valueOf(id);
            String mess="";
            int request=3;
            AlertDialog.Builder builder = new AlertDialog.Builder(ShopOrderDetailSellerActivity.this);

            mess = "Bạn có chắc chắn muốn hủy đơn hàng!!!";
            builder.setTitle("Deni Order");
            builder.setMessage(mess);

            String CheckRQ=String.valueOf(request);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Handle the "OK" button click if needed
                    RequestQueue queue = Volley.newRequestQueue(ShopOrderDetailSellerActivity.this);
                    String url = AcceptOrder;

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("Tasas",response);
                                    if (response.equals("success")){
                                        String remess= "Đã hủy sản phẩm thành công!";
                                        RequestDB.showInvalidOtpDialogAcceptOrder(ShopOrderDetailSellerActivity.this,remess);
                                    }else if (response.equals("failed")){
                                        String remess= "hủy sản phẩm thất bại!";
                                        RequestDB.showInvalidOtpDialogERROR(ShopOrderDetailSellerActivity.this,remess);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ShopOrderDetailSellerActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String,String> params = new HashMap<>();
                            params.put("order_id",order_id);
                            params.put("request",CheckRQ);
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            // Must call show() prior to fetching text view
            TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
            messageView.setGravity(Gravity.CENTER);

            TextView titleView = (TextView)alertDialog.findViewById(ShopOrderDetailSellerActivity.this.getResources().getIdentifier("alertTitle", "id", "android"));
            if (titleView != null) {
                titleView.setGravity(Gravity.CENTER);
            }
        }
    }
}