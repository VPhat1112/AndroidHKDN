package com.example.apphkdn.activity;

import static com.example.apphkdn.activity.CartActivity.cartLists;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.adapter.ProductOrderAdapter;

import java.text.DecimalFormat;

public class CheckOutActivity extends AppCompatActivity {
    RecyclerView rcv_Order;
    TextView txtTenandSDT,txtMoneyFast,txtMoneyShip,txtMoneyTotal,CheckMoneyTotal,Update_Address_order;
    ProductOrderAdapter productOrderAdapter;
    int totalBill = 0,totalSP=0;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        UnitUI();
        setData();
    }
    private void setData(){
        productOrderAdapter = new ProductOrderAdapter(CheckOutActivity.this, cartLists);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(CheckOutActivity.this, 1, RecyclerView.VERTICAL, false);
        rcv_Order.setAdapter(productOrderAdapter);
        rcv_Order.setLayoutManager(linearLayoutManager);
        txtTenandSDT.setText(DataLocalManager.getNameUser()+" | "+DataLocalManager.getPhoneUser());
        totalMoney();
        txtMoneyFast.setText(decimalFormat.format(totalBill)+"đ");
        txtMoneyShip.setText("42,000 đ");
        txtMoneyTotal.setText(decimalFormat.format(totalBill+42000)+"đ");

        CheckMoneyTotal.setText(decimalFormat.format(totalBill+42000)+"đ");
        Update_Address_order.setText(DataLocalManager.getAddressUser());
    }
    private void totalMoney(){
        for (int j=0;j<cartLists.size();j++){
            if (cartLists.get(j).getCheck().equals("1")){
                totalBill= totalBill+cartLists.get(j).getProduct_price()*cartLists.get(j).getProduct_pay();
                totalSP++;
            }
        }
    }
    private void UnitUI(){
        rcv_Order=findViewById(R.id.rcv_Order);
        txtTenandSDT=findViewById(R.id.txtTenandSDT);
        txtMoneyFast=findViewById(R.id.txtMoneyFast);
        txtMoneyShip=findViewById(R.id.txtMoneyShip);
        txtMoneyTotal=findViewById(R.id.txtMoneyTotal);
        CheckMoneyTotal=findViewById(R.id.CheckMoneyTotal);
        Update_Address_order=findViewById(R.id.Update_Address_order);
    }
}