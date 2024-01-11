package com.example.apphkdn.activity;

import static com.example.apphkdn.ultil.Server.getProductOder;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.R;
import com.example.apphkdn.RequestDB.RequestDB;
import com.example.apphkdn.adapter.product_Order_adapter;
import com.example.apphkdn.model.order_product;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ShopOrderDetailBuyerActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton back_Btn_Detail, btn_danhgia;
    ImageView img_producr_buyer_order;
    RecyclerView rcv_productOrder;
    TextView txtOrder_ID,txtCreatedAt,StatusDetail,tv_name_buyer_order,txtSDTDetail,txtAddress,tv_name_shop_seller_order,tv_product_name_order,tv_product_quantity_order,tv_product_price_order;
    Integer Order_Id,Status,Price,Quantity,Shop_id,id,Product_price;
    String CreatedAt,NameBuyer,SDT,Address,Name_Shop,Product_Name,Image,shop_image,user_name;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    RequestDB requestDB = new RequestDB();
    product_Order_adapter productOrderAdapter;
    ArrayList<order_product> orderProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_order_detail_for_buyer);
        UnitUI();
        GetData();
        setData();
        EventAction();SetRCV();

    }
    private void EventAction(){

        back_Btn_Detail.setOnClickListener(this);
    }

    private void GetData(){
        //GetData
        id= Integer.valueOf(getIntent().getStringExtra("id"));
        Shop_id= Integer.valueOf(getIntent().getStringExtra("Shop_id"));
        CreatedAt= getIntent().getStringExtra("CreatedAt");
        Status= Integer.valueOf(getIntent().getStringExtra("status"));
        SDT=getIntent().getStringExtra("SDT");
        Address=getIntent().getStringExtra("Address_ship");
        Name_Shop=getIntent().getStringExtra("shopName");
        shop_image=getIntent().getStringExtra("shop_image");
        Product_price= Integer.valueOf(getIntent().getStringExtra("Product_price"));
        user_name=getIntent().getStringExtra("user_name");
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
        tv_name_shop_seller_order.setText(Name_Shop);
    }
    private void SetRCV(){
        orderProducts = new ArrayList<>();
        productOrderAdapter = new product_Order_adapter(ShopOrderDetailBuyerActivity.this,orderProducts);
        rcv_productOrder.setHasFixedSize(true);
        rcv_productOrder.setLayoutManager(new GridLayoutManager(ShopOrderDetailBuyerActivity.this,1));
        rcv_productOrder.setAdapter(productOrderAdapter);
        requestDB.GetProductOrder(ShopOrderDetailBuyerActivity.this,orderProducts,productOrderAdapter,getProductOder+String.valueOf(id));
    }

    private void UnitUI(){
        back_Btn_Detail=findViewById(R.id.back_Btn_Detail);
        txtOrder_ID=findViewById(R.id.txtOrder_ID);
        txtCreatedAt=findViewById(R.id.txtCreatedAt);
        StatusDetail=findViewById(R.id.StatusDetail);
        tv_name_buyer_order=findViewById(R.id.tv_name_buyer_order);
        txtSDTDetail=findViewById(R.id.txtSDTDetail);
        txtAddress=findViewById(R.id.txtAddress);
        tv_name_shop_seller_order=findViewById(R.id.tv_name_shop_seller_order);
        rcv_productOrder=findViewById(R.id.rcv_product_order_detail);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_Btn_Detail){
            finish();
        }
    }
}