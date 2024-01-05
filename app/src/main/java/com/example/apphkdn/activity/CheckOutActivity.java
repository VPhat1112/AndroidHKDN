package com.example.apphkdn.activity;

import static com.example.apphkdn.activity.CartActivity.cartLists;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphkdn.DataLocalManager.DataLocalManager;
import com.example.apphkdn.R;
import com.example.apphkdn.adapter.ProductOrderAdapter;
import com.example.apphkdn.model.Cart;
import com.example.apphkdn.ultil.ChoiceWayPayDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CheckOutActivity extends AppCompatActivity implements ChoiceWayPayDialog.ChoiceWayPayDialogListener {
    RecyclerView rcv_Order;
    TextView txtTenandSDT,txtMoneyFast,txtMoneyShip,txtMoneyTotal,CheckMoneyTotal,Update_Address_order,Comfirm_order,txtSeall,txtWayPay;
    Button btn_Order_complete;
    ProductOrderAdapter productOrderAdapter;
    String[] listItems;

    ArrayList<Integer> WayPayItems=new ArrayList<>();
    int totalBill = 0,totalSP=0;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        UnitUI();
        EventAction();
    }
    private void EventAction(){
        Comfirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_Order_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        setData();
        setWayPay();
    }
    private void setWayPay(){
        txtSeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment WaypayChoice=new ChoiceWayPayDialog();
                WaypayChoice.setCancelable(false);
                WaypayChoice.show(getSupportFragmentManager(),"WayPay Choice");
            }
        });
    }
    private void setData(){
        List<Cart> filteredCartList = new ArrayList<>();
        for (int j=0;j<cartLists.size();j++){
            if (cartLists.get(j).getCheck().equals("1")){
                filteredCartList.add(cartLists.get(j));
            }
        }

        productOrderAdapter = new ProductOrderAdapter(CheckOutActivity.this, (ArrayList<Cart>) filteredCartList);
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
        Comfirm_order=findViewById(R.id.Comfirm_order);
        btn_Order_complete=findViewById(R.id.btn_Order_complete);
        txtSeall=findViewById(R.id.txtSeall);
        txtWayPay=findViewById(R.id.txtWayPay);
    }

    @Override
    public void onPotisitisionClicked(String[] list, int position) {
        txtWayPay.setText(list[position]);
    }
}